/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection;

import java.lang.reflect.Method;

public final class ReflectedStatusSessionHandler {

    private static final Class<?> clazz;
    private final Object original;

    static {
        try {
            clazz = Class.forName("com.velocitypowered.proxy.connection.client.StatusSessionHandler");
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ReflectedStatusSessionHandler(ReflectedMinecraftSessionHandler handler) { this.original = handler.original(); }

    public Object passOriginalMethod(Method method, Object[] args) throws ReflectiveOperationException {
        return clazz.getMethod(method.getName(), method.getParameterTypes()).invoke(original, args);
    }

}
