/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.connection.client;

import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftSessionHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class ReflectedStatusSessionHandler {

    private static final Class<?> clazz;
    private static final Constructor<?> constructor;
    private final Object original;

    static {
        try {
            clazz = ClassList.STATUS_SESSION_HANDLER;

            constructor = clazz.getDeclaredConstructor(ClassList.VELOCITY_SERVER, ClassList.VELOCITY_INBOUND_CONNECTION);
            constructor.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ReflectedStatusSessionHandler(ReflectedInitialInboundConnection inbound) throws ReflectiveOperationException {
        original = constructor.newInstance(Celest.getServer(), inbound.original());
    }

    public Object passOriginalMethod(Method method, Object[] args) throws ReflectiveOperationException {
        return clazz.getMethod(method.getName(), method.getParameterTypes()).invoke(original, args);
    }

}
