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
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.PermissionType;
import xyz.dashnetwork.celest.utils.chat.ColorUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.List;
import java.util.Optional;

public final class CommandNickName extends CelestCommand {

    public CommandNickName() {
        super("nickname", "nick");

        addArguments(ArgumentType.STRING);
        addArguments(User::isAdmin, true, ArgumentType.PLAYER_LIST);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<String> optional = arguments.get(String.class);
        List<Player> players = arguments.playerListOrSelf(source);

        if (optional.isEmpty() || players.isEmpty()) {
            sendUsage(source, label);
            MessageUtils.message(source, "&6&l»&7 \"/nickname off\" can be used to clear your nickname.");
            return;
        }

        String string = optional.get();
        boolean off = string.equals("off");

        if (!off && !PermissionType.ADMIN.hasPermission(source)) {
            int length = ColorUtils.strip(string).length();

            if (length < 4 || length > 16) {
                MessageUtils.message(source, "&6&l»&c Your nickname cannot be longer than 16 characters.");
                return;
            }
        }

        if (off)
            string = null;
        else
            string = ColorUtils.fromAmpersand(string);

        NamedSource named = NamedSource.of(source);
        MessageBuilder builder;

        for (Player player : players) {
            User user = User.getUser(player);
            user.getData().setNickName(string);

            builder = new MessageBuilder();

            if (off)
                builder.append("&6&l»&7 Your nickname has been cleared");
            else
                builder.append("&6&l»&7 Your nickname has been set to &6" + string);

            if (!source.equals(player)) {
                builder.append("&7 by ");
                builder.append(new NamedSourceFormat(named));
            }

            MessageUtils.message(player, builder::build);
        }

        if (source instanceof Player) {
            players.remove(source);

            if (players.size() > 0) {
                builder = new MessageBuilder();
                builder.append("&6&l»&7 Nicknames for ");
                builder.append(new PlayerFormat(players));

                if (off)
                    builder.append("&7 have been cleared");
                else
                    builder.append("&7 have been set to &6" + string);

                MessageUtils.message(source, builder::build);
            }
        }
    }

}
