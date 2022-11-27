/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.Command;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerListFormat;

import java.util.List;
import java.util.function.Predicate;

public final class CommandClearChat extends Command {

    // TODO: Tab Completion

    public CommandClearChat() {
        super("clearchat", "cc");

        arguments(true, ArgumentType.PLAYER_LIST);
        permission(User::isStaff, true);
    }

    @Override
    protected void execute(CommandSource source, Arguments arguments) {
        NamedSource named = new NamedSource(source);
        List<Player> players = arguments.getPlayerList();
        MessageBuilder builder = new MessageBuilder();
        Predicate<User> sendSelf = user -> players.size() == 1 || !user.getPlayer().equals(source);

        for (int i = 0; i < 99; i++)
            builder.append("\n");

        builder.append("&6&l» &7Chat was cleared by ").onlyIf(sendSelf);
        builder.append(new PlayerFormat(named)).onlyIf(sendSelf);

        for (Player player : players)
            MessageUtils.message(player, builder::build);

        if (ListUtils.containsOtherThan(players, source)) {
            builder = new MessageBuilder();
            builder.append("&6&l» &7Chat was cleared for ");
            builder.append(new PlayerListFormat(players));

            MessageUtils.message(source, builder::build);
        }
    }

}
