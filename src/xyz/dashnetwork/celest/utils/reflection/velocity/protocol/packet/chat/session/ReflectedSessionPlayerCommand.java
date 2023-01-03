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

package xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.chat.session;

import xyz.dashnetwork.celest.utils.reflection.ClassList;

import java.lang.reflect.Field;

public final class ReflectedSessionPlayerCommand {

    private static final Class<?> clazz;
    private static final Class<?>[] array;
    private static Field salt;
    private final Object original;

    static {
        clazz = ClassList.SESSION_PLAYER_COMMAND;
        array = new Class<?>[] { clazz };

        try {
            salt = clazz.getDeclaredField("salt");
            salt.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public static Class<?>[] array() { return array; }

    public ReflectedSessionPlayerCommand(Object original) { this.original = original; }

    public Object original() { return original; }

    public void setSalt(long value) throws ReflectiveOperationException { salt.set(original, value); }

}
