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

package xyz.dashnetwork.celest.inject.reflection.velocity.connection;

import xyz.dashnetwork.celest.inject.reflection.ClassList;

public final class ReflectedMinecraftSessionHandler {

    private static final Class<?> clazz = ClassList.MINECRAFT_SESSION_HANDLER;
    private static final ClassLoader loader = clazz.getClassLoader();
    private static final Class<?>[] array = new Class<?>[] { clazz };
    private final Object original;

    public static ClassLoader loader() { return loader; }

    public static Class<?>[] array() { return array; }

    public ReflectedMinecraftSessionHandler(Object original) { this.original = original; }

    public Object original() { return original; }

}
