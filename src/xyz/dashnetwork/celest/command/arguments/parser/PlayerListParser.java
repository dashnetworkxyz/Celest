/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parser;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.utils.FunctionPair;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class PlayerListParser implements FunctionPair<User, String, Player[]> {

    @Override
    public Player[] apply(User user, String string) {
        List<Player> list = new ArrayList<>();

        if (string.equalsIgnoreCase("@a")) {
            for (User each : User.getUsers())
                if (user == null || user.canSee(each))
                    list.add(each.getPlayer());
        } else {
            for (String each : string.split(",")) {
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
