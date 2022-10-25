/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import xyz.dashnetwork.celest.utils.BufUtils;

import java.util.List;

@ChannelHandler.Sharable
public final class DecodeHandler extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf buffer, List<Object> list) {
        System.out.println("test");

        ByteBuf copy = buffer.copy(); // copy buffer so Velocity can use it.

        int packetId = BufUtils.readVarInt(buffer);
        // Packet.handle(phase, packetId, buffer);

        System.out.println(packetId);

        // TODO: event.setCancelled(true) ????
        list.add(copy);
    }

}
