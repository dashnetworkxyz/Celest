/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.ArrayUtils;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;
import java.util.function.Predicate;

public final class CommandClearChat extends CelestCommand {

    // TODO: Tab Completion

    public CommandClearChat() {
        super("clearchat", "cc");

        setPermission(User::isStaff, true);
        addArguments(ArgumentType.PLAYER_LIST);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<Player[]> optional = arguments.get(Player[].class);

        if (optional.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        NamedSource named = NamedSource.of(source);
        Player[] players = optional.get();
        MessageBuilder builder = new MessageBuilder();
        Predicate<User> notSelf = user -> players.length == 1 || !user.getPlayer().equals(source);

        for (int i = 0; i < 99; i++)
            builder.append("\n");

        builder.append("&6&l» &7Chat was cleared by ").onlyIf(notSelf);
        builder.append(new PlayerFormat(named)).onlyIf(notSelf);

        for (Player player : players)
            MessageUtils.message(player, builder::build);

        if (ArrayUtils.containsOtherThan(players, source)) {
            builder = new MessageBuilder();
            builder.append("&6&l» &7Chat was cleared for ");
            builder.append(new PlayerFormat(players));

            MessageUtils.message(source, builder::build);
        }
    }

}
