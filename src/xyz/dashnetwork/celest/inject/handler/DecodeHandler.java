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
    protected void decode(ChannelHandlerContext context, ByteBuf buf, List<Object> out) {
        out.add(buf.copy());

        int id = BufUtils.readVarInt(buf);

        System.out.println("test: " + id);

        if (id == 0x37)
            System.out.println("player info found");
    }

}
