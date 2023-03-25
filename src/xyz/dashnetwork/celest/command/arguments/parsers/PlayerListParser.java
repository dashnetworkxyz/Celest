/*
 * Copyright (C) 2023 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parsers;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Parser;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.HashSet;
import java.util.Set;

public final class PlayerListParser implements Parser {

    @Override
    public Object parse(User user, String input) {
        Set<Player> set = new HashSet<>();

        if (input.equalsIgnoreCase("@a")) {
            for (User each : User.getUsers())
                if (user == null || user.canSee(each))
                    set.add(each.getPlayer());
        } else {
            for (String each : input.split(",")) {
                Object player = ArgumentType.PLAYER.parse(user, each);

                if (player != null)
                    set.add((Player) player);
            }
        }

        if (set.isEmpty())
            return null;

        return set.toArray(Player[]::new);
    }

}
