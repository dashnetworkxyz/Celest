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

import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.inject.reflection.ClassList;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectedStatusSessionHandler {

    private static final Class<?> clazz = ClassList.STATUS_SESSION_HANDLER;
    private static Constructor<?> constructor;
    private static Field pingReceived;
    private final Object original;

    static {
        try {
            constructor = clazz.getDeclaredConstructor(ClassList.VELOCITY_SERVER, ClassList.VELOCITY_INBOUND_CONNECTION);
            constructor.setAccessible(true);

            pingReceived = clazz.getDeclaredField("pingReceived");
            pingReceived.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedStatusSessionHandler(ReflectedInitialInboundConnection inbound) throws ReflectiveOperationException {
        original = constructor.newInstance(Celest.getServer(), inbound.original());
    }

    public Object passOriginalMethod(Method method, Object[] args) throws ReflectiveOperationException {
        return clazz.getMethod(method.getName(), method.getParameterTypes()).invoke(original, args);
    }

    public boolean getPingReceived() throws ReflectiveOperationException {
        return pingReceived.getBoolean(original);
    }

    public void setPingReceived(boolean received) throws ReflectiveOperationException {
        pingReceived.set(original, received);
    }

}
