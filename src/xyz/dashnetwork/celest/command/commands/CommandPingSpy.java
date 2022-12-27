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
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.ArrayList;
import java.util.List;

public final class CommandPingSpy extends CelestCommand {

    public CommandPingSpy() {
        super("pingspy", "ps");

        setPermission(User::isAdmin, true);
        addArguments(User::isOwner, true, ArgumentType.PLAYER_LIST);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        List<Player> players = arguments.playerListOrSelf(source);

        if (players.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        List<Player> on = new ArrayList<>();
        List<Player> off = new ArrayList<>();
        MessageBuilder builder;

        for (Player player : players) {
            User user = User.getUser(player);
            UserData data = user.getData();
            boolean spy = !data.getPingSpy();

            data.setPingSpy(spy);

            builder = new MessageBuilder();
            builder.append("&6&l»&7 You are ");

            if (spy) {
                builder.append("now");

                if (!source.equals(player))
                    on.add(player);
            } else {
                builder.append("no longer");

                if (!source.equals(player))
                    off.add(player);
            }

            builder.append(" in &6PingSpy");

            MessageUtils.message(player, builder::build);
        }

        if (on.size() > 0) {
            builder = new MessageBuilder();
            builder.append("&6&l»&7 ");
            builder.append(new PlayerFormat(on));
            builder.append("&7 now in &6PingSpy");

            MessageUtils.message(source, builder::build);
        }

        if (off.size() > 0) {
            builder = new MessageBuilder();
            builder.append("&6&l»&7 ");
            builder.append(new PlayerFormat(off));
            builder.append("&7 no longer in &6PingSpy");

            MessageUtils.message(source, builder::build);
        }
    }

}
