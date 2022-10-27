/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject;

import com.velocitypowered.api.proxy.ProxyServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.inject.handler.CelestChannelInitializer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class Injector {

    public static void injectPacketListener() {
        ProxyServer server = Celest.getServer();

        try {
            Field cm = server.getClass().getDeclaredField("cm");
            cm.setAccessible(true);

            Object connectionManager = cm.get(server);
            Class<?> connectionManagerClass = connectionManager.getClass();
            Object channelInitializerHolder = connectionManagerClass.getDeclaredMethod("getServerChannelInitializer").invoke(connectionManager);
            ChannelInitializer<?> channelInitializer = (ChannelInitializer<?>) channelInitializerHolder.getClass().getDeclaredMethod("get").invoke(channelInitializerHolder);

            Field initializer = channelInitializerHolder.getClass().getDeclaredField("initializer");
            initializer.setAccessible(true);
            initializer.set(channelInitializerHolder, new CelestChannelInitializer(channelInitializer));
        } catch (Exception exception) {
            Celest.getLogger().error("Failed to inject packet listener. API change?");
        }
    }

}
