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

package xyz.dashnetwork.celest.utils.reflection.velocity.connection.client;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.backend.ReflectedVelocityServerConnection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectedConnectedPlayer {

    private static final Class<?> clazz;
    private static Field playerKey;
    private static Method getConnectedServer;
    private final Object original;

    static {
        clazz = ClassList.CONNECTED_PLAYER;

        try {
            playerKey = clazz.getDeclaredField("playerKey");
            playerKey.setAccessible(true);

            getConnectedServer = clazz.getMethod("getConnectedServer");
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedConnectedPlayer(Player player) { this.original = player; }

    public void setPlayerKey(Object object) throws ReflectiveOperationException {
        playerKey.set(original, object);
    }

    public ReflectedVelocityServerConnection getConnectedServer() throws ReflectiveOperationException {
        return new ReflectedVelocityServerConnection(getConnectedServer.invoke(original));
    }

}
