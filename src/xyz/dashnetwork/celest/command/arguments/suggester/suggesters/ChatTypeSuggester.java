/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.suggester.suggesters;

import xyz.dashnetwork.celest.command.arguments.suggester.ArugmentSuggester;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class ChatTypeSuggester implements ArugmentSuggester {

    @Override
    public List<String> suggest(User user, String input) {
        List<String> list = new ArrayList<>();

        for (ChatType type : ChatType.values())
            if (type.hasPermission(user))
                ListUtils.addIfStarts(list, input, type.name().toLowerCase());

        return list;
    }

}
