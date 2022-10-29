/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.handler;

import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.events.CelestHandshakeEvent;
import xyz.dashnetwork.celest.utils.reflection.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public final class CelestHandshakeHandler implements InvocationHandler {

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
            CelestHandshakeEvent event = new CelestHandshakeEvent(
                    null, // TODO: add InboundConnection (will require overriding the entire method)
                    handshake.getProtocolVersion(),
                    handshake.getServerAddress(),
                    handshake.getPort(),
                    handshake.getNextStatus()
            );

            Celest.getServer().getEventManager().fire(event);

            if (!event.getResult().isAllowed()) {
                // TODO
            }

            handler.passOriginalMethod(method, args);

            if (handshake.getNextStatus() == 1) {
                ReflectedStatusSessionHandler statusHandler = new ReflectedStatusSessionHandler(connection.getSessionHandler());

                connection.setSessionHandler(Proxy.newProxyInstance(
                        ReflectedMinecraftSessionHandler.loader(),
                        ReflectedMinecraftSessionHandler.array(),
                        new CelestStatusHandler(statusHandler, connection)
                ));
            }

            return true;
        } else
            return handler.passOriginalMethod(method, args);
    }

}
