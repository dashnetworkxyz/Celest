/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.handler;

import io.netty.channel.ChannelHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class ReflectedHandshakeHandler implements InvocationHandler {

    private static final Class<?> handshake;
    private static final Class<?>[] handshakeArray;
    private static final Method getState;
    private final Object original;
    private final ChannelHandler channelHandler;

    static {
        try {
            handshake = Class.forName("com.velocitypowered.proxy.protocol.packet.Handshake");
            handshakeArray = new Class<?>[] { handshake };

            Class<?> minecraftConnection = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnection");
            getState = minecraftConnection.getMethod("getState");
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ReflectedHandshakeHandler(Object original, ChannelHandler channelHandler) {
        this.original = original;
        this.channelHandler = channelHandler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws ReflectiveOperationException {
        String name = method.getName();
        Class<?>[] types = method.getParameterTypes();

        Method originalMethod = original.getClass().getMethod(name, types);
        Object invokedMethod = originalMethod.invoke(original, args);

        if (originalMethod.getName().equals("handle") && Arrays.equals(originalMethod.getParameterTypes(), handshakeArray)) {
            Enum<?> state = (Enum<?>) getState.invoke(channelHandler);

            switch (state.name()) { // TODO: inject custom handlers
                case "STATUS":
                    System.out.println("status detected");
                    break;
                case "LOGIN":
                    System.out.println("login detected");
                    break;
            }
        }

        return invokedMethod;
    }

}
