/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.connection.util;

import com.velocitypowered.api.proxy.server.ServerPing;
import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedInitialInboundConnection;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

public final class ReflectedServerListPingHandler {

    private static final Class<?> clazz;
    private static Method getInitialPing;
    private final Object original;

    static {
        clazz = ClassList.SERVER_LIST_PING_HANDLER;

        try {
            getInitialPing = clazz.getMethod("getInitialPing", ClassList.VELOCITY_INBOUND_CONNECTION);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedServerListPingHandler(Object original) { this.original = original; }

    @SuppressWarnings("unchecked")
    public CompletableFuture<ServerPing> getInitialPing(ReflectedInitialInboundConnection inbound) throws ReflectiveOperationException {
        return (CompletableFuture<ServerPing>) getInitialPing.invoke(original, inbound.original());
    }

}
