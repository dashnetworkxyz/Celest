/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.channel.channels.output;

import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.utils.connection.User;

public class ChannelDisplayName extends Channel {

    @Override
    protected void handle(User user) {
        output.writeUTF(user.getUuid().toString());
        output.writeUTF(user.getDisplayname());
    }

}
