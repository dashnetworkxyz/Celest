/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.protocol.packet;

public final class ReflectedStatusRequest {

    private static final Class<?> clazz;
    private static final Class<?>[] array;

    static {
        try {
            clazz = Class.forName("com.velocitypowered.proxy.protocol.packet.StatusRequest");
            array = new Class<?>[] { clazz };
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Class<?>[] array() { return array; }

}
