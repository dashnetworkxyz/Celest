/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.connection.util;

import com.velocitypowered.api.proxy.server.ServerPing;
import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedInitialInboundConnection;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

public final class ReflectedServerListPingHandler {

    private static final Class<?> clazz;
    private static Method getInitialPing;
    private final Object original;

    static {
        clazz = ClassList.SERVER_LIST_PING_HANDLER;

        try {
            getInitialPing = clazz.getMethod("getInitialPing", ClassList.VELOCITY_INBOUND_CONNECTION);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedServerListPingHandler(Object original) { this.original = original; }

    @SuppressWarnings("unchecked")
    public CompletableFuture<ServerPing> getInitialPing(ReflectedInitialInboundConnection inbound) throws ReflectiveOperationException {
        return (CompletableFuture<ServerPing>) getInitialPing.invoke(original, inbound.original());
    }

}
