/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;

public final class CommandFakeLeave extends CelestCommand {

    public CommandFakeLeave() {
        super("fakeleave");

        setPermission(User::isOwner, true);
        addArguments(ArgumentType.STRING, ArgumentType.MESSAGE);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<String> optional1 = arguments.get(String.class);
        Optional<String> optional2 = arguments.get(String.class);

        if (optional1.isEmpty() || optional2.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        String username = optional1.get();
        String displayname = optional2.get();

        MessageBuilder builder = new MessageBuilder();
        builder.append("&c&l»&r ");
        builder.append(displayname).hover(username);
        builder.append("&c left.");

        MessageUtils.broadcast(each -> !each.isStaff(), builder::build);

        builder = new MessageBuilder();
        builder.append("&6&l»&r ");
        builder.append(displayname).hover(username);
        builder.append("&7 fake-left by ");
        builder.append(new NamedSourceFormat(NamedSource.of(source)));

        MessageUtils.broadcast(User::isStaff, builder::build);
    }

}
