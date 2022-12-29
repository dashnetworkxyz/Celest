/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.chat.session;

import xyz.dashnetwork.celest.utils.reflection.ClassList;

import java.lang.reflect.Field;

public final class ReflectedSessionPlayerCommand {

    private static final Class<?> clazz;
    private static final Class<?>[] array;
    private static Field salt;
    private final Object original;

    static {
        clazz = ClassList.SESSION_PLAYER_COMMAND;
        array = new Class<?>[] { clazz };

        try {
            salt = clazz.getDeclaredField("salt");
            salt.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public static Class<?>[] array() { return array; }

    public ReflectedSessionPlayerCommand(Object original) { this.original = original; }

    public Object original() { return original; }

    public void setSalt(long value) throws ReflectiveOperationException { salt.set(original, value); }

}
