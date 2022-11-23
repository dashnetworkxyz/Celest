/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity;

import com.google.gson.Gson;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.ProxyServer;
import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.util.ReflectedServerListPingHandler;
import xyz.dashnetwork.celest.utils.reflection.velocity.network.ReflectedConnectionManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectedVelocityServer {

    private static final Class<?> clazz;
    private static final Field cm;
    private static final Method getPingGsonInstance, getServerListPingHandler;
    private final ProxyServer original;

    static {
        clazz = ClassList.VELOCITY_SERVER;

        try {
            cm = clazz.getDeclaredField("cm");
            cm.setAccessible(true);

            getPingGsonInstance = clazz.getMethod("getPingGsonInstance", ProtocolVersion.class);
            getServerListPingHandler = clazz.getMethod("getServerListPingHandler");
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ReflectedVelocityServer(ProxyServer original) { this.original = original; }

    public static Gson getPingGsonInstance(ProtocolVersion version) throws ReflectiveOperationException {
        return (Gson) getPingGsonInstance.invoke(null, version);
    }

    public ReflectedConnectionManager getConnectionManager() throws ReflectiveOperationException {
        return new ReflectedConnectionManager(cm.get(original));
    }

    public ReflectedServerListPingHandler getServerListPingHandler() throws ReflectiveOperationException {
        return new ReflectedServerListPingHandler(getServerListPingHandler.invoke(original)); // TODO
    }

}
