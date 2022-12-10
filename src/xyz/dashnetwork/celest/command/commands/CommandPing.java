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
import xyz.dashnetwork.celest.utils.ArgumentUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;

public class CommandPing extends CelestCommand {

    public CommandPing() {
        super("ping", "p");

        addArguments(ArgumentType.PLAYER);
    }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        Player selected = ArgumentUtils.playerOrSelf(source, arguments);

        if (selected == null) {
            sendUsage(source, label);
            return;
        }

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 ");
        builder.append(new PlayerFormat(selected));
        builder.append("&7 ping: &6" + selected.getPing() + "ms");

        MessageUtils.message(source, builder::build);
    }

}
