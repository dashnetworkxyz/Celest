/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.connection;

import xyz.dashnetwork.celest.utils.reflection.ClassList;

public final class ReflectedMinecraftSessionHandler {

    private static final Class<?> clazz;
    private static final ClassLoader loader;
    private static final Class<?>[] array;
    private final Object original;

    static {
        clazz = ClassList.MINECRAFT_SESSION_HANDLER;
        loader = clazz.getClassLoader();
        array = new Class<?>[] { clazz };
    }

    public static ClassLoader loader() { return loader; }

    public static Class<?>[] array() { return array; }

    public ReflectedMinecraftSessionHandler(Object original) { this.original = original; }

    public Object original() { return original; }

}
