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

package xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet;

import xyz.dashnetwork.celest.utils.reflection.ClassList;

import java.lang.reflect.Constructor;

public final class ReflectedStatusResponse {

    private static final Class<?> clazz = ClassList.STATUS_RESPONSE;
    private static Constructor<?> constructor;
    private final Object original;

    static {
        try {
            constructor = clazz.getConstructor(CharSequence.class);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedStatusResponse(CharSequence sequence) throws ReflectiveOperationException {
        original = constructor.newInstance(sequence);
    }

    public Object original() { return original; }

}
