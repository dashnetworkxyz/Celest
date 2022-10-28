/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import xyz.dashnetwork.celest.utils.ReflectionUtils;

import java.lang.reflect.Proxy;

public final class CelestChannelInitializer extends ChannelInitializer<Channel> {

    private final ChannelInitializer<?> original;

    public CelestChannelInitializer(ChannelInitializer<?> original) { this.original = original; }

    @Override
    protected void initChannel(Channel channel) throws ReflectiveOperationException {
        ReflectionUtils.invokeInitChannel(original, channel);

        ChannelHandler channelHandler = channel.pipeline().get("handler");
        Object handshakeHandler = ReflectionUtils.invokeGetSessionHandler(channelHandler);

        Object instance = Proxy.newProxyInstance(
                ReflectionUtils.loaderMinecraftSessionHandler(),
                ReflectionUtils.arrayMinecraftSessionHandler(),
                new ReflectedHandshakeHandler(handshakeHandler, channelHandler)
        );

        ReflectionUtils.invokeSetSessionHandler(channelHandler, instance);
    }

}
