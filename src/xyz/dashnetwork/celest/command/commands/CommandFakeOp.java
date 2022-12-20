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
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CommandFakeOp extends CelestCommand {

    public CommandFakeOp() {
        super("fakeop");

        setPermission(User::isOwner, true);
        addArguments(ArgumentType.PLAYER_LIST);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<Player[]> optional = arguments.get(Player[].class);

        if (optional.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        String username = source instanceof Player ? ((Player) source).getUsername() : "Server";
        List<Player> players = new ArrayList<>(List.of(optional.get()));
        MessageBuilder builder;

        for (Player player : players) {
            builder = new MessageBuilder();
            builder.append("&7&o[" + username + ": Opped " + player.getUsername());

            MessageUtils.message(player, builder::build);
        }

        if (ListUtils.containsOtherThan(players, source)) {
            if (source instanceof Player)
                players.remove(source);

            builder = new MessageBuilder();
            builder.append("&6&l»&7 Fake op sent to ");
            builder.append(new PlayerFormat(players));

            MessageUtils.message(source, builder::build);
        }
    }

}
