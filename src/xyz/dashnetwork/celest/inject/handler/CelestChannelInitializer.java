/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class CelestChannelInitializer extends ChannelInitializer<Channel> {

    private static final Class<?> sessionHandler;
    private static final ClassLoader sessionHandlerLoader;
    private static final Class<?>[] sessionHandlerArray;
    private static final Method initChannel, getSessionHandler, setSessionHandler;
    private final ChannelInitializer<?> original;

    static {
        try { // More RAM, but vroom vroom
            sessionHandler = Class.forName("com.velocitypowered.proxy.connection.MinecraftSessionHandler");
            sessionHandlerLoader = sessionHandler.getClassLoader();
            sessionHandlerArray = new Class<?>[] { sessionHandler };

            Class<?> minecraftConnection = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnection");
            getSessionHandler = minecraftConnection.getMethod("getSessionHandler");
            setSessionHandler = minecraftConnection.getMethod("setSessionHandler", sessionHandler);

            initChannel = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
            initChannel.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public CelestChannelInitializer(ChannelInitializer<?> original) { this.original = original; }

    @Override
    protected void initChannel(Channel channel) throws ReflectiveOperationException {
        initChannel.invoke(original, channel);

        ChannelHandler channelHandler = channel.pipeline().get("handler");
        Object instance = Proxy.newProxyInstance(
                sessionHandlerLoader,
                sessionHandlerArray,
                new ReflectedHandshakeHandler(getSessionHandler.invoke(channelHandler), channelHandler)
        );

        setSessionHandler.invoke(channelHandler, instance);
    }

}
