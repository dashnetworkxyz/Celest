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

package xyz.dashnetwork.celest.inject.server.handler;

import com.velocitypowered.api.network.ProtocolVersion;
import xyz.dashnetwork.celest.inject.reflection.velocity.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.inject.reflection.velocity.connection.client.ReflectedHandshakeSessionHandler;
import xyz.dashnetwork.celest.inject.reflection.velocity.connection.client.ReflectedInitialInboundConnection;
import xyz.dashnetwork.celest.inject.reflection.velocity.protocol.packet.ReflectedHandshake;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class CelestHandshakeSessionHandler implements InvocationHandler {

    private final ReflectedHandshakeSessionHandler handler;
    private final ReflectedMinecraftConnection connection;

    public CelestHandshakeSessionHandler(ReflectedHandshakeSessionHandler handler, ReflectedMinecraftConnection connection) {
        this.handler = handler;
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws ReflectiveOperationException {
        String name = method.getName();
        Class<?>[] types = method.getParameterTypes();

        if (name.equals("handle") && Arrays.equals(types, ReflectedHandshake.array())) {
            ReflectedHandshake handshake = new ReflectedHandshake(args[0]);
            ProtocolVersion version = handshake.getProtocolVersion();
            String cleaned = handler.cleanVhost(handshake.getServerAddress());
            ReflectedInitialInboundConnection inbound = new ReflectedInitialInboundConnection(connection, cleaned, handshake);
            Enum<?> state = (Enum<?>) handler.getStateForProtocol(handshake.getNextStatus());
            boolean domain = handshake.getServerAddress().toLowerCase().contains("dashnetwork");

            if (state == null || !domain)
                connection.close(true);
            else {
                connection.setState(state);
                connection.setProtocolVersion(version);
                connection.setAssociation(inbound);

                switch (state.name()) {
                    case "STATUS" -> connection.setSessionHandler(new CelestStatusSessionHandler(inbound, connection));
                    case "LOGIN" -> handler.handleLogin(handshake, inbound);
                    default -> throw new AssertionError("getStateForProtocol provided invalid state!");
                }
            }

            return true;
        }

        return handler.passOriginalMethod(method, args);
    }

}
