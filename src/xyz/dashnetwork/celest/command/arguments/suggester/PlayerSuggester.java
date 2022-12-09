/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.suggester;

import xyz.dashnetwork.celest.utils.FunctionPair;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class PlayerSuggester implements FunctionPair<User, String, List<String>> {

    @Override
    public List<String> apply(User user, String input) {
        List<String> list = new ArrayList<>();

        ListUtils.addIfStarts(list, input, "@s");

        for (User each : User.getUsers())
            if (user.isStaff() || !each.getData().getVanish() || user.getData().getVanish())
                ListUtils.addIfStarts(list, input, user.getUsername());

        return list;
    }

}
