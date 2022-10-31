/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.network;

import java.lang.reflect.Method;

public final class ReflectedConnectionManager {

    private static final Class<?> clazz;
    private static final Method getServerChannelInitializer;
    private final Object original;

    static {
        try {
            clazz = Class.forName("com.velocitypowered.proxy.network.ConnectionManager");
            getServerChannelInitializer = clazz.getMethod("getServerChannelInitializer");
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ReflectedConnectionManager(Object original) { this.original = original; }

    public ReflectedServerChannelInitializerHolder getServerChannelInitializer() throws ReflectiveOperationException {
        return new ReflectedServerChannelInitializerHolder(getServerChannelInitializer.invoke(original));
    }

}
