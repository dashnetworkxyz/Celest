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

package xyz.dashnetwork.celest.utils.reflection.velocity.connection;

import xyz.dashnetwork.celest.utils.reflection.ClassList;

public final class ReflectedMinecraftSessionHandler {

    private static final Class<?> clazz;
    private static final ClassLoader loader;
    private static final Class<?>[] array;
    private final Object original;

    static {
        clazz = ClassList.MINECRAFT_SESSION_HANDLER;
        loader = clazz.getClassLoader();
        array = new Class<?>[] { clazz };
    }

    public static ClassLoader loader() { return loader; }

    public static Class<?>[] array() { return array; }

    public ReflectedMinecraftSessionHandler(Object original) { this.original = original; }

    public Object original() { return original; }

}
