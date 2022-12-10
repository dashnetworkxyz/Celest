/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.backend.handler;

import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedClientPlaySessionHandler;
import xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.chat.session.ReflectedSessionPlayerChat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class CelestClientPlaySessionHandler implements InvocationHandler {

    private final ReflectedClientPlaySessionHandler handler;

    public CelestClientPlaySessionHandler(ReflectedClientPlaySessionHandler handler) {
        this.handler = handler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws ReflectiveOperationException {
        String name = method.getName();
        Class<?>[] types = method.getParameterTypes();

        if (name.equals("handle") && Arrays.equals(types, ReflectedSessionPlayerChat.array())) {
            ReflectedSessionPlayerChat chat = new ReflectedSessionPlayerChat(args[0]);
            chat.setSigned(false);

            args[0] = chat.original();
        }

        return handler.passOriginalMethod(method, args);
    }

}
