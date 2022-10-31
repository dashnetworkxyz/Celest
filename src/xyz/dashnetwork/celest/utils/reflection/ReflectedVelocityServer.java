/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection;

import com.velocitypowered.api.proxy.ProxyServer;
import xyz.dashnetwork.celest.utils.reflection.network.ReflectedConnectionManager;

import java.lang.reflect.Field;

public final class ReflectedVelocityServer {

    private static final Class<?> clazz;
    private static final Field cm;
    private ProxyServer original;

    static {
        try {
            clazz = Class.forName("com.velocitypowered.proxy.VelocityServer");

            cm = clazz.getDeclaredField("cm");
            cm.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ReflectedVelocityServer(ProxyServer original) { this.original = original; }

    public ReflectedConnectionManager getConnectionManager() throws ReflectiveOperationException {
        return new ReflectedConnectionManager(cm.get(original));
    }

}
