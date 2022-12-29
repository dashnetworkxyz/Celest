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
import xyz.dashnetwork.celest.utils.chat.ColorUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerProfileFormat;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;

public final class CommandRealName extends CelestCommand {

    public CommandRealName() {
        super("realname");

        addArguments(ArgumentType.STRING);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<String> optional = arguments.get(String.class);

        if (optional.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        String search = optional.get();
        MessageBuilder builder = new MessageBuilder();

        for (User user : User.getUsers()) {
            String nickname = user.getData().getNickName();

            if (nickname != null && ColorUtils.strip(nickname).toLowerCase().contains(search.toLowerCase())) {
                if (builder.length() > 0)
                    builder.append("\n");

                builder.append("&6&l»&7 ");
                builder.append(new NamedSourceFormat(user));
                builder.append("&7 real name is ");
                builder.append(new PlayerProfileFormat(user)).prefix("&6");
            }
        }

        if (builder.length() == 0)
            builder.append("&6&l»&7 No player found for &6" + search);

        MessageUtils.message(source, builder::build);
    }

}
