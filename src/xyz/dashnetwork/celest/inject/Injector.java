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
import xyz.dashnetwork.celest.utils.reflection.ReflectedConnectionManager;
import xyz.dashnetwork.celest.utils.reflection.ReflectedServerChannelInitializerHolder;
import xyz.dashnetwork.celest.utils.reflection.ReflectedVelocityServer;

public final class Injector {

    public static void injectSessionListener() {
        try {
            ReflectedVelocityServer server = new ReflectedVelocityServer(Celest.getServer());
            ReflectedConnectionManager connectionManager = server.getConnectionManager();
            ReflectedServerChannelInitializerHolder holder = connectionManager.getServerChannelInitializer();
            ChannelInitializer<?> initializer = holder.get();

            holder.set(new CelestChannelInitializer(initializer));
        } catch (ReflectiveOperationException | RuntimeException exception) {
            Celest.getLogger().error("Failed to inject packet listener. Printing stacktrace...");
            exception.printStackTrace();
        }
    }

}
