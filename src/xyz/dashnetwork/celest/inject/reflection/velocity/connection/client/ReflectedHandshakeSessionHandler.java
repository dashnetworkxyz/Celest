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
import xyz.dashnetwork.celest.inject.reflection.velocity.connection.ReflectedMinecraftSessionHandler;
import xyz.dashnetwork.celest.inject.reflection.velocity.protocol.packet.ReflectedHandshake;

import java.lang.reflect.Method;

public final class ReflectedHandshakeSessionHandler {

    private static final Class<?> clazz = ClassList.HANDSHAKE_SESSION_HANDLER;
    private static Method cleanVhost, getStateForProtocol, handleLogin;
    private final Object original;

    static {
        try {
            cleanVhost = clazz.getDeclaredMethod("cleanVhost", String.class);
            getStateForProtocol = clazz.getDeclaredMethod("getStateForProtocol", int.class);
            handleLogin = clazz.getDeclaredMethod("handleLogin", ClassList.HANDSHAKE, ClassList.INITIAL_INBOUND_CONNECTION);

            cleanVhost.setAccessible(true);
            getStateForProtocol.setAccessible(true);
            handleLogin.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedHandshakeSessionHandler(ReflectedMinecraftSessionHandler handler) { this.original = handler.original(); }

    public Object passOriginalMethod(Method method, Object[] args) throws ReflectiveOperationException {
        return clazz.getMethod(method.getName(), method.getParameterTypes()).invoke(original, args);
    }

    public String cleanVhost(String hostname) throws ReflectiveOperationException {
        return (String) cleanVhost.invoke(original, hostname);
    }

    public Object getStateForProtocol(int status) throws ReflectiveOperationException {
        return getStateForProtocol.invoke(original, status);
    }

    public void handleLogin(ReflectedHandshake handshake, ReflectedInitialInboundConnection inbound) throws ReflectiveOperationException {
        handleLogin.invoke(original, handshake.original(), inbound.original());
    }

}
