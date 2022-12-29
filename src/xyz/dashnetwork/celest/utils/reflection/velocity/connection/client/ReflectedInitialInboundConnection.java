/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.connection.client;

import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.ReflectedHandshake;

import java.lang.reflect.Constructor;

public final class ReflectedInitialInboundConnection {

    private static final Class<?> clazz;
    private static Constructor<?> constructor;
    private final Object original;

    static {
        clazz = ClassList.INITIAL_INBOUND_CONNECTION;

        try {
            constructor = clazz.getDeclaredConstructor(ClassList.MINECRAFT_CONNECTION, String.class, ClassList.HANDSHAKE);
            constructor.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedInitialInboundConnection(ReflectedMinecraftConnection connection, String address, ReflectedHandshake handshake) throws ReflectiveOperationException {
        original = constructor.newInstance(connection.original(), address, handshake.original());
    }

    public Object original() { return original; }

}
