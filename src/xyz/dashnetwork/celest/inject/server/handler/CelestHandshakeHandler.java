/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.server.handler;

import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.InboundConnection;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.events.CelestHandshakeEvent;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedHandshakeSessionHandler;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedInitialInboundConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.ReflectedHandshake;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class CelestHandshakeHandler implements InvocationHandler {

    private static final EventManager eventManager = Celest.getServer().getEventManager();
    private final ReflectedHandshakeSessionHandler handler;
    private final ReflectedMinecraftConnection connection;

    public CelestHandshakeHandler(ReflectedHandshakeSessionHandler handler, ReflectedMinecraftConnection connection) {
        this.handler = handler;
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws ReflectiveOperationException {
        String name = method.getName();
        Class<?>[] types = method.getParameterTypes();

        if (name.equals("handle") && Arrays.equals(types, ReflectedHandshake.array())) {
            ReflectedHandshake handshake = new ReflectedHandshake(args[0]);
            String address = handshake.getServerAddress();
            ProtocolVersion version = handshake.getProtocolVersion();
            int next = handshake.getNextStatus();

            if (!address.toLowerCase().contains("dashnetwork")) {
                connection.close(true);
                return true;
            }

            String cleaned = handler.cleanVhost(address);
            ReflectedInitialInboundConnection inbound = new ReflectedInitialInboundConnection(connection, cleaned, handshake);
            Enum<?> state = (Enum<?>) handler.getStateForProtocol(next);

            if (state == null) {
                connection.close(true);
                return true;
            } else {
                connection.setState(state);
                connection.setProtocolVersion(version);
                connection.setAssociation(inbound);

                switch (state.name()) {
                    case "STATUS":
                        connection.setSessionHandler(new CelestStatusHandler(inbound, connection));
                        break;
                    case "LOGIN":
                        handler.handleLogin(handshake, inbound);
                        break;
                    default:
                        throw new AssertionError("getStateForProtocol provided invalid state!");
                }
            }

            eventManager.fireAndForget(new CelestHandshakeEvent(
                    (InboundConnection) inbound.original(),
                    version,
                    address,
                    handshake.getPort(),
                    next
            ));

            return true;
        } else
            return handler.passOriginalMethod(method, args);
    }

}
