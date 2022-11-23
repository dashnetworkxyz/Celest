/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.chat;

import xyz.dashnetwork.celest.utils.reflection.ClassList;

import java.lang.reflect.Field;

public final class ReflectedPlayerChat {

    private static final Class<?> clazz;
    private static final Class<?>[] array;
    private static final Field unsigned;
    private final Object original;

    static {
        clazz = ClassList.PLAYER_CHAT;
        array = new Class<?>[] { clazz };

        try {
            unsigned = clazz.getDeclaredField("unsigned");
            unsigned.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Class<?>[] array() { return array; }

    public ReflectedPlayerChat(Object original) { this.original = original; }

    public Object original() { return original; }

    public void setUnsigned(boolean value) throws ReflectiveOperationException {
        unsigned.set(original, value);
    }

}
