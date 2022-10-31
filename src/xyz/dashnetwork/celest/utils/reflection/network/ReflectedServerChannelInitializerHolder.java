/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.network;

import io.netty.channel.ChannelInitializer;

import java.lang.reflect.Field;

public final class ReflectedServerChannelInitializerHolder {

    private static final Class<?> clazz;
    private static final Field initializer; // Use field to avoid warning message.
    private final Object original;

    static {
        try {
            clazz = Class.forName("com.velocitypowered.proxy.network.ServerChannelInitializerHolder");

            initializer = clazz.getDeclaredField("initializer");
            initializer.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ReflectedServerChannelInitializerHolder(Object original) { this.original = original; }

    public ChannelInitializer<?> get() throws ReflectiveOperationException {
        return (ChannelInitializer<?>) initializer.get(original);
    }

    public void set(ChannelInitializer<?> channelInitializer) throws ReflectiveOperationException {
        initializer.set(original, channelInitializer);
    }

}
