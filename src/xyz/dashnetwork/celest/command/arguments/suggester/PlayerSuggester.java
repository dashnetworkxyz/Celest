/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.suggester;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.utils.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerSuggester implements FunctionPair<CommandSource, String, List<String>> {

    @Override
    public List<String> apply(CommandSource source, String input) {
        User user = CastUtils.toUser(source);
        List<String> list = new ArrayList<>();

        ListUtils.addIfStarts(list, input, "@s");

        for (User each : User.getUsers())
            if (PermissionUtils.checkVanished(user, each))
                ListUtils.addIfStarts(list, input, user.getUsername());

        return list;
    }

}
