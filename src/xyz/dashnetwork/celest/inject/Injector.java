/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject;

import com.velocitypowered.api.proxy.ProxyServer;
import io.netty.channel.ChannelInitializer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.inject.handler.CelestChannelInitializer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class Injector {

    public static boolean injectPacketListener() {
        ProxyServer server = Celest.getServer();

        try {
            Field field = server.getClass().getDeclaredField("cm");
            field.setAccessible(true);

            Object connectionManager = field.get(server);
            Object channelInitializerHolder = connectionManager.getClass().getDeclaredMethod("getBackendChannelInitializer").invoke(connectionManager);
            ChannelInitializer<?> channelInitializer = (ChannelInitializer<?>) channelInitializerHolder.getClass().getDeclaredMethod("get").invoke(channelInitializerHolder);

            // This is a deprecated method. Hopefully it isn't removed.
            Method method = channelInitializerHolder.getClass().getMethod("set", ChannelInitializer.class);
            method.invoke(channelInitializerHolder, new CelestChannelInitializer(channelInitializer));

            return true; // Injection successful.
        } catch (Exception exception) {
            Celest.getLogger().error("Failed to inject packet listener. API change?");

            return false;
        }
    }

}
