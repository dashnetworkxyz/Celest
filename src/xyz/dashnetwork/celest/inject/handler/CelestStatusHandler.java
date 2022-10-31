/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.handler;

import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedInitialInboundConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedStatusSessionHandler;
import xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.ReflectedStatusRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class CelestStatusHandler implements InvocationHandler {

    private final ReflectedStatusSessionHandler handler;
    private final ReflectedMinecraftConnection connection;

    public CelestStatusHandler(ReflectedInitialInboundConnection inbound, ReflectedMinecraftConnection connection) throws ReflectiveOperationException {
        this.handler = new ReflectedStatusSessionHandler(inbound);
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws ReflectiveOperationException {
        String name = method.getName();
        Class<?>[] types = method.getParameterTypes();

        if (name.equals("handle") && Arrays.equals(types, ReflectedStatusRequest.array())) {
            // TODO: override method for custom json element.
        }

        return handler.passOriginalMethod(method, args);
    }

}
