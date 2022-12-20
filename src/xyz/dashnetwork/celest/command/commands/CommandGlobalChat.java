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
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.List;

public final class CommandGlobalChat extends CelestCommand {

    public CommandGlobalChat() {
        super("globalchat", "gc");

        addArguments(User::isOwner, true, ArgumentType.PLAYER_LIST);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        List<Player> players = ArgumentUtils.playerListOrSelf(source, arguments);

        if (players.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        NamedSource named = NamedSource.of(source);
        MessageBuilder builder;

        for (Player player : players) {
            User user = User.getUser(player);
            user.getData().setChatType(ChatType.GLOBAL);

            builder = new MessageBuilder();
            builder.append("&6&l»&7 You have been moved to &6GlobalChat");

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
                builder.append("&6&l»&7 You have moved ");
                builder.append(new PlayerFormat(players));
                builder.append("&7 to &6GlobalChat");

                MessageUtils.message(source, builder::build);
            }
        }
    }

}
