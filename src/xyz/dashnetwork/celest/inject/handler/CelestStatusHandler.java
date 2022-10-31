/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.handler;

import xyz.dashnetwork.celest.utils.reflection.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.utils.reflection.connection.client.ReflectedStatusSessionHandler;
import xyz.dashnetwork.celest.utils.reflection.protocol.packet.ReflectedStatusRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class CelestStatusHandler implements InvocationHandler {

    private final ReflectedStatusSessionHandler handler;
    private final ReflectedMinecraftConnection connection;

    public CelestStatusHandler(ReflectedStatusSessionHandler handler, ReflectedMinecraftConnection connection) {
        this.handler = handler;
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws ReflectiveOperationException {
        String name = method.getName();
        Class<?>[] types = method.getParameterTypes();

        if (name.equals("handle") && Arrays.equals(types, ReflectedStatusRequest.array())) {
            // TODO: override method for custom json element.

            System.out.println("request handle invoked");
        }

        return handler.passOriginalMethod(method, args);
    }

}
