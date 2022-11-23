/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.connection.client;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.backend.ReflectedVelocityServerConnection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectedConnectedPlayer {

    private static final Class<?> clazz;
    private static final Field playerKey;
    private static final Method getConnectedServer;
    private final Object original;

    static {
        clazz = ClassList.CONNECTED_PLAYER;

        try {
            playerKey = clazz.getDeclaredField("playerKey");
            playerKey.setAccessible(true);

            getConnectedServer = clazz.getMethod("getConnectedServer");
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ReflectedConnectedPlayer(Player player) { this.original = player; }

    public void setPlayerKey(Object object) throws ReflectiveOperationException {
        playerKey.set(original, object);
    }

    public ReflectedVelocityServerConnection getConnectedServer() throws ReflectiveOperationException {
        return new ReflectedVelocityServerConnection(getConnectedServer.invoke(original));
    }

}
