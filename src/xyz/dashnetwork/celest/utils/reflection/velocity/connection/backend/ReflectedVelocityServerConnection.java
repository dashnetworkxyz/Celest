/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.connection.backend;

import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftConnection;

import java.lang.reflect.Method;

public class ReflectedVelocityServerConnection {

    private static final Class<?> clazz;
    private static final Method ensureConnected;
    private final Object original;

    static {
        try {
            clazz = ClassList.VELOCITY_SERVER_CONNECTION;
            ensureConnected = clazz.getMethod("ensureConnected");
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ReflectedVelocityServerConnection(Object original) { this.original = original; }

    public ReflectedMinecraftConnection ensureConnected() throws ReflectiveOperationException {
        return new ReflectedMinecraftConnection(ensureConnected.invoke(original));
    }

}
