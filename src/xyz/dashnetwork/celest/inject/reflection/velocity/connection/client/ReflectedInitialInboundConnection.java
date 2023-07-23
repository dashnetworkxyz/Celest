/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.inject.reflection.velocity.connection.client;

import xyz.dashnetwork.celest.inject.reflection.ClassList;
import xyz.dashnetwork.celest.inject.reflection.velocity.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.inject.reflection.velocity.protocol.packet.ReflectedHandshake;

import java.lang.reflect.Constructor;

public final class ReflectedInitialInboundConnection {

    private static final Class<?> clazz = ClassList.INITIAL_INBOUND_CONNECTION;
    private static Constructor<?> constructor;
    private final Object original;

    static {
        try {
            constructor = clazz.getDeclaredConstructor(ClassList.MINECRAFT_CONNECTION, String.class, ClassList.HANDSHAKE);
            constructor.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedInitialInboundConnection(ReflectedMinecraftConnection connection, String address, ReflectedHandshake handshake) throws ReflectiveOperationException {
        original = constructor.newInstance(connection.original(), address, handshake.original());
    }

    public Object original() { return original; }

}
