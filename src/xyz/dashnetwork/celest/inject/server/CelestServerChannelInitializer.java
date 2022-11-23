/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import xyz.dashnetwork.celest.inject.server.handler.CelestHandshakeSessionHandler;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedHandshakeSessionHandler;

import java.lang.reflect.Method;

public final class CelestServerChannelInitializer extends ChannelInitializer<Channel> {

    private static final Method initChannel;
    private final ChannelInitializer<?> original;

    static {
        try {
            initChannel = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
            initChannel.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public CelestServerChannelInitializer(ChannelInitializer<?> original) { this.original = original; }

    @Override
    protected void initChannel(Channel channel) throws ReflectiveOperationException {
        initChannel.invoke(original, channel);

        ReflectedMinecraftConnection connection = new ReflectedMinecraftConnection(channel.pipeline().get("handler"));
        ReflectedHandshakeSessionHandler handler = new ReflectedHandshakeSessionHandler(connection.getSessionHandler());

        connection.setSessionHandler(new CelestHandshakeSessionHandler(handler, connection));
    }

}
