/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.PermissionUtils;
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.arguments.ArgumentType;
import xyz.dashnetwork.celest.utils.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.Messages;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;

import java.util.List;

public final class CommandClearChat implements SimpleCommand {

    // TODO: Tab Completion

    @Override
    public boolean hasPermission(Invocation invocation) {
        return PermissionUtils.checkSource(invocation.source(), User::isAdmin, true);
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        Arguments arguments = new Arguments(source, invocation.arguments(),
                ArgumentType.PLAYER_LIST);

        if (arguments.isEmpty()) {
            MessageUtils.message(source, Messages.commandUsage(invocation.alias(),
                    "<players>"));
            return;
        }

        List<Player> players = arguments.getPlayerList();
        MessageBuilder builder = new MessageBuilder();

        for (int i = 0; i < 99; i++)
            builder.append("\n");

        builder.append("&6&l» &7Chat was cleared by &6TODO: impl"); // TODO: NameUtils?

        for (Player player : players)
            MessageUtils.message(player, builder.build());

        // TODO: Send message to source with players affected.
    }

}
