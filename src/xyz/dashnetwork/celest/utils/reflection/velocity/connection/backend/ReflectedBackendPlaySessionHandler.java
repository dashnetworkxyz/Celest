/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.connection.backend;

import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftSessionHandler;

import java.lang.reflect.Field;

public final class ReflectedBackendPlaySessionHandler {

    private static final Class<?> clazz;
    private static Field playerConnection;
    private final Object original;

    static {
        clazz = ClassList.BACKEND_PLAY_SESSION_HANDLER;

        try {
            playerConnection = clazz.getDeclaredField("playerConnection");
            playerConnection.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedBackendPlaySessionHandler(ReflectedMinecraftSessionHandler handler) { this.original = handler.original(); }

    public ReflectedMinecraftConnection playerConnection() throws ReflectiveOperationException {
        return new ReflectedMinecraftConnection(playerConnection.get(original));
    }

}
