/*
 * Copyright (C) 2023 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.suggesters;

import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Suggester;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class PlayerListSuggester implements Suggester {

    @Override
    public List<String> suggest(User user, String input) {
        List<String> list = new ArrayList<>();
        String[] split = input.split(",");
        int length = split.length - 1;

        if (length < 0)
            return list;

        boolean ends = input.endsWith(",");
        String last = split[length];
        String remaining = StringUtils.unsplit(ends ? 0 : -1, ",", split);

        if (!remaining.isBlank())
            remaining += ",";

        if (ends)
            last = "";

        ListUtils.addIfStarts(list, input, "@a");
        List<String> playerSuggest = ArgumentType.PLAYER.suggest(user, last);

        for (String entry : playerSuggest)
            ListUtils.addIfStarts(list, input, remaining + entry);

        return list;
    }

}
