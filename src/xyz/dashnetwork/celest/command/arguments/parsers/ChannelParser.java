/*
 * Copyright (C) 2023 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parsers;

import xyz.dashnetwork.celest.command.arguments.Parser;
import xyz.dashnetwork.celest.utils.chat.Channel;
import xyz.dashnetwork.celest.utils.connection.User;

public final class ChannelParser implements Parser {

    @Override
    public Object parse(User user, String input) {
        for (Channel type : Channel.values())
            if (type.hasPermission(user) && type.name().equalsIgnoreCase(input))
                return type;

        return null;
    }

}
