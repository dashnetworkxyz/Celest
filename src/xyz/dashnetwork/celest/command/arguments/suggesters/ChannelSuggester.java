/*
 * Copyright (C) 2023 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.suggesters;

import xyz.dashnetwork.celest.command.arguments.Suggester;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.chat.Channel;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class ChannelSuggester implements Suggester {

    @Override
    public List<String> suggest(User user, String input) {
        List<String> list = new ArrayList<>();

        for (Channel type : Channel.values())
            if (type.hasPermission(user))
                ListUtils.addIfStarts(list, input, type.name().toLowerCase());

        return list;
    }

}
