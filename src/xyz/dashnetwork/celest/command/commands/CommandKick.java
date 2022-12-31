/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.List;

public final class CommandKick extends CelestCommand {

    public CommandKick() {
        super("kick");

        setPermission(User::isAdmin, true);
        addArguments(ArgumentType.PLAYER_LIST);
        addArguments(ArgumentType.MESSAGE);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        List<Player> players = arguments.playerListOrSelf(source);

        if (players.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        NamedSource named = NamedSource.of(source);
        String reason = arguments.get(String.class).orElse("No reason provided.");

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&lDashNetwork");
        builder.append("\n&7You have been kicked");
        builder.append("\n&7You were kicked by &6" + named.getUsername());
        builder.append("\n\n" + reason);

        Component message = builder.build(null);

        builder = new MessageBuilder();
        builder.append("&6&l»&6");

        for (Player player : players) {
            builder.append(" " + player.getUsername());
            player.disconnect(message);
        }

        builder.append("&7 kicked by");
        builder.append(new NamedSourceFormat(named));

        MessageUtils.broadcast(builder::build);
    }

}
