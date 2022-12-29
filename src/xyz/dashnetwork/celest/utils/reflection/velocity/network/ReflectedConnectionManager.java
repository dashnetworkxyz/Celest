/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.network;

import xyz.dashnetwork.celest.utils.reflection.ClassList;

import java.lang.reflect.Method;

public final class ReflectedConnectionManager {

    private static final Class<?> clazz;
    private static Method getServerChannelInitializer;
    private final Object original;

    static {
        clazz = ClassList.CONNECTION_MANAGER;

        try {
            getServerChannelInitializer = clazz.getMethod("getServerChannelInitializer");
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedConnectionManager(Object original) { this.original = original; }

    public ReflectedServerChannelInitializerHolder getServerChannelInitializer() throws ReflectiveOperationException {
        return new ReflectedServerChannelInitializerHolder(getServerChannelInitializer.invoke(original));
    }

}
