/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.backend.handler;

import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedClientPlaySessionHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CelestPlaySessionHandler implements InvocationHandler {

    private final ReflectedClientPlaySessionHandler handler;

    public CelestPlaySessionHandler(ReflectedClientPlaySessionHandler handler) {
        this.handler = handler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws ReflectiveOperationException {
        // TODO: Override handle(PlayerChat) method

        return handler.passOriginalMethod(method, args);
    }

}
