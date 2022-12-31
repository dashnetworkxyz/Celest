/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parser.parsers;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.parser.Parser;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class PlayerListParser implements Parser {

    @Override
    public Object parse(User user, String input) {
        List<Player> list = new ArrayList<>();

        if (input.equalsIgnoreCase("@a")) {
            for (User each : User.getUsers())
                if (user == null || user.canSee(each))
                    list.add(each.getPlayer());
        } else {
            for (String each : input.split(",")) {
                Object player = ArgumentType.PLAYER.parse(user, each);

                if (player != null)
                    list.add((Player) player);
            }
        }

        if (list.isEmpty())
            return null;

        return list.toArray(Player[]::new);
    }

}
