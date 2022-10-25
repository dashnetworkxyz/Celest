/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class CelestChannelInitializer extends ChannelInitializer<Channel> {

    private static Method initChannel;
    private final ChannelInitializer<?> original;

    static {
        try {
            initChannel = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
            initChannel.setAccessible(true);
        } catch (NoSuchMethodException exception) {
            exception.printStackTrace();
        }
    }

    public CelestChannelInitializer(ChannelInitializer<?> original) { this.original = original; }

    @Override
    protected void initChannel(Channel channel) throws InvocationTargetException, IllegalAccessException {
        initChannel.invoke(original, channel);

        channel.pipeline().addFirst("celest-listener", new DecodeHandler());
    }

}
