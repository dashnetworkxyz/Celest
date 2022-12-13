/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.channel.channels.input;

import com.google.common.io.ByteArrayDataInput;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.utils.PermissionType;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.function.Predicate;

public final class ChannelBroadcast extends Channel {

    @Override
    public void handle(ByteArrayDataInput input) {
        boolean json = input.readBoolean();
        Predicate<User> permission = PermissionType.valueOf(input.readUTF()).getPermission();
        String string = input.readUTF();

        if (json)
            MessageUtils.broadcast(permission, ComponentUtils.fromJson(string));
        else
            MessageUtils.broadcast(permission, string);
    }

}
