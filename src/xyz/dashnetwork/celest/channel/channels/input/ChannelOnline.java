/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.channel.channels.input;

import com.google.common.io.ByteArrayDataInput;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.utils.connection.User;

public final class ChannelOnline extends Channel {

    @Override
    public void handle(ByteArrayDataInput input) {
        boolean showVanished = input.readBoolean();
        int online = 0;

        for (User user : User.getUsers())
            if (showVanished || !user.getData().getVanish())
                online++;

        output.writeInt(online);
    }

}
