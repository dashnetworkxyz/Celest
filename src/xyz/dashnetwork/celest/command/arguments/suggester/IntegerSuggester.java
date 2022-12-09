/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.suggester;

import xyz.dashnetwork.celest.utils.FunctionPair;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class IntegerSuggester implements FunctionPair<User, String, List<String>> {

    @Override
    public List<String> apply(User user, String input) {
        List<String> list = new ArrayList<>();

        if (!input.isBlank() && !StringUtils.matchesInteger(input))
            return list;

        for (int i = 0; i < 10; i++)
            list.add(input + i);

        return list;
    }

}
