/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.connection.backend;

import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftSessionHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectedBackendPlaySessionHandler {

    private static final Class<?> clazz = ClassList.BACKEND_PLAY_SESSION_HANDLER;
    private static Field playerConnection;
    private final Object original;

    static {
        try {
            playerConnection = clazz.getDeclaredField("playerConnection");
            playerConnection.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedBackendPlaySessionHandler(ReflectedMinecraftSessionHandler handler) { this.original = handler.original(); }

    public ReflectedMinecraftConnection playerConnection() throws ReflectiveOperationException {
        return new ReflectedMinecraftConnection(playerConnection.get(original));
    }

}
