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

package xyz.dashnetwork.celest.inject.reflection.velocity;

import com.google.gson.Gson;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.ProxyServer;
import xyz.dashnetwork.celest.inject.reflection.ClassList;
import xyz.dashnetwork.celest.inject.reflection.velocity.connection.util.ReflectedServerListPingHandler;
import xyz.dashnetwork.celest.inject.reflection.velocity.network.ReflectedConnectionManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectedVelocityServer {

    private static final Class<?> clazz = ClassList.VELOCITY_SERVER;
    private static Field cm;
    private static Method getPingGsonInstance, getServerListPingHandler;
    private final ProxyServer original;

    static {
        try {
            cm = clazz.getDeclaredField("cm");
            cm.setAccessible(true);

            getPingGsonInstance = clazz.getMethod("getPingGsonInstance", ProtocolVersion.class);
            getServerListPingHandler = clazz.getMethod("getServerListPingHandler");
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedVelocityServer(ProxyServer original) { this.original = original; }

    public static Gson getPingGsonInstance(ProtocolVersion version) throws ReflectiveOperationException {
        return (Gson) getPingGsonInstance.invoke(null, version);
    }

    public ReflectedConnectionManager getConnectionManager() throws ReflectiveOperationException {
        return new ReflectedConnectionManager(cm.get(original));
    }

    public ReflectedServerListPingHandler getServerListPingHandler() throws ReflectiveOperationException {
        return new ReflectedServerListPingHandler(getServerListPingHandler.invoke(original)); // TODO
    }

}
