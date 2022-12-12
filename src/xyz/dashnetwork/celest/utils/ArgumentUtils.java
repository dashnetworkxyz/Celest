/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.arguments.ArgumentSection;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ArgumentUtils {

    public static List<ArgumentType> typesFromSections(CommandSource source, List<ArgumentSection> sections) {
        List<ArgumentType> list = new ArrayList<>();
        User user = CastUtils.toUser(source);

        for (ArgumentSection section : sections)
            if (user == null ? section.allowsConsole() : section.getPredicate().test(user))
                list.addAll(List.of(section.getArgumentTypes()));

        return list;
    }

    public static Player playerOrSelf(CommandSource source, Arguments arguments) {
        Optional<Player> optional = arguments.get(Player.class);

        if (optional.isEmpty()) {
            if (source instanceof Player)
                return (Player) source;

            return null;
        }

        return optional.get();
    }

    public static List<Player> playerListOrSelf(CommandSource source, Arguments arguments) {
        List<Player> list = new ArrayList<>();
        Optional<Player[]> optional = arguments.get(Player[].class);

        if (optional.isEmpty()) {
            if (source instanceof Player)
                list.add((Player) source);
            return list;
        }

        list.addAll(List.of(optional.get()));

        return list;
    }

}
