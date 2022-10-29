/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection;

import java.lang.reflect.Method;

public final class ReflectedMinecraftConnection {

    private static final Class<?> clazz;
    private static final Method getSessionHandler, setSessionHandler;
    private final Object original;

    static {
        try {
            clazz = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnection");

            getSessionHandler = clazz.getMethod("getSessionHandler");
            setSessionHandler = clazz.getMethod("setSessionHandler", ReflectedMinecraftSessionHandler.clazz());
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ReflectedMinecraftConnection(Object original) { this.original = original; }

    public ReflectedMinecraftSessionHandler getSessionHandler() throws ReflectiveOperationException {
        return new ReflectedMinecraftSessionHandler(getSessionHandler.invoke(original));
    }

    public void setSessionHandler(Object object) throws ReflectiveOperationException {
        setSessionHandler.invoke(original, object);
    }

}
