/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject;

import io.netty.channel.ChannelInitializer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.inject.handler.CelestChannelInitializer;
import xyz.dashnetwork.celest.utils.ReflectionUtils;

public final class Injector {

    public static void injectSessionListener() {
        try {
            Object connectionManager = ReflectionUtils.getDeclaredField(Celest.getServer(), "cm");
            Object channelInitializerHolder = ReflectionUtils.invokeDeclaredMethod(connectionManager, "getServerChannelInitializer");
            ChannelInitializer<?> channelInitializer = (ChannelInitializer<?>) ReflectionUtils.invokeDeclaredMethod(channelInitializerHolder, "get");

            ReflectionUtils.setDeclaredField(channelInitializerHolder, "initializer", new CelestChannelInitializer(channelInitializer));
        } catch (ReflectiveOperationException | RuntimeException exception) {
            Celest.getLogger().error("Failed to inject packet listener. Printing stacktrace...");
            exception.printStackTrace();
        }
    }

}
