/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.channel.in;

import com.google.common.io.ByteArrayDataInput;
import xyz.dashnetwork.celest.channel.Channel;

import java.util.ArrayList;
import java.util.List;

public final class ChannelInSubscribe implements Channel.Inbound {

    @Override
    public void read(String serverName, ByteArrayDataInput input) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < input.readInt(); i++)
            list.add(input.readUTF());

        // Add subscribed channels to some place to store it.
    }

}
