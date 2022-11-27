/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.suggester;

import xyz.dashnetwork.celest.utils.FunctionPair;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.chat.ChatType;

import java.util.ArrayList;
import java.util.List;

public final class ChatTypeSuggester implements FunctionPair<User, String, List<String>> {

    @Override
    public List<String> apply(User user, String input) {
        List<String> list = new ArrayList<>();

        for (ChatType type : ChatType.values())
            if (type.hasPermission(user))
                ListUtils.addIfStarts(list, input, type.name().toLowerCase());

        return list;
    }

}
