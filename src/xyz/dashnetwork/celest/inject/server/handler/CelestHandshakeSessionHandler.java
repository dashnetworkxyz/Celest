/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.server.handler;

import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.network.ProtocolVersion;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedHandshakeSessionHandler;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedInitialInboundConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.ReflectedHandshake;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class CelestHandshakeSessionHandler implements InvocationHandler {

    private static final EventManager eventManager = Celest.getServer().getEventManager();
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

            if (state == null) {
                connection.close(true);
                return true;
            } else {
                connection.setState(state);
                connection.setProtocolVersion(version);
                connection.setAssociation(inbound);

                switch (state.name()) {
                    case "STATUS":
                        connection.setSessionHandler(new CelestStatusSessionHandler(inbound, connection));
                        break;
                    case "LOGIN":
                        handler.handleLogin(handshake, inbound);
                        break;
                    default:
                        throw new AssertionError("getStateForProtocol provided invalid state!");
                }
            }

            return true;
        } else
            return handler.passOriginalMethod(method, args);
    }

}
