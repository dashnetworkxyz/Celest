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

package xyz.dashnetwork.celest.utils.reflection.velocity.connection.backend;

import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftConnection;

import java.lang.reflect.Method;

public final class ReflectedVelocityServerConnection {

    private static final Class<?> clazz;
    private static Method ensureConnected;
    private final Object original;

    static {
        clazz = ClassList.VELOCITY_SERVER_CONNECTION;

        try {
            ensureConnected = clazz.getMethod("ensureConnected");
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedVelocityServerConnection(Object original) { this.original = original; }

    public ReflectedMinecraftConnection ensureConnected() throws ReflectiveOperationException {
        return new ReflectedMinecraftConnection(ensureConnected.invoke(original));
    }

}
