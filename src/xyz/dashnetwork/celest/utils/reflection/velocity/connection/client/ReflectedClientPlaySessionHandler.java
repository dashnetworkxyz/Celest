/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.connection.client;

import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftSessionHandler;

import java.lang.reflect.Method;

public final class ReflectedClientPlaySessionHandler {

    private static final Class<?> clazz;
    private static Method validateChat;
    private final Object original;

    static {
        clazz = ClassList.CLIENT_PLAY_SESSION_HANDLER;

        try {
            validateChat = clazz.getDeclaredMethod("validateChat", String.class);
            validateChat.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedClientPlaySessionHandler(ReflectedMinecraftSessionHandler handler) { this.original = handler.original(); }

    public Object passOriginalMethod(Method method, Object[] args) throws ReflectiveOperationException {
        return clazz.getMethod(method.getName(), method.getParameterTypes()).invoke(original, args);
    }

    public boolean validateChat(String message) throws ReflectiveOperationException {
        return (boolean) validateChat.invoke(original, message);
    }

}
