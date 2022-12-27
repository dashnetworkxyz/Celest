/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ServerConnection;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.CastUtils;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.*;

public final class CommandGlobalList extends CelestCommand {

    public CommandGlobalList() { super("globallist", "glist", "list"); }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Map<String, List<NamedSource>> map = new TreeMap<>(String::compareTo);
        User user = CastUtils.toUser(source);

        for (User each : User.getUsers()) {
            if (user == null || user.canSee(each)) {
                Optional<ServerConnection> optional = each.getPlayer().getCurrentServer();

                if (optional.isEmpty())
                    continue;

                String name = optional.get().getServerInfo().getName();
                List<NamedSource> list = map.getOrDefault(name, new ArrayList<>());

                list.add(each);
                map.put(name, list);
            }
        }

        if (map.isEmpty()) {
            MessageUtils.message(source, "&6&l»&7 No players online.");
            return;
        }

        MessageBuilder builder = new MessageBuilder();

        for (Map.Entry<String, List<NamedSource>> entry : map.entrySet()) {
            if (builder.length() > 0)
                builder.append("\n");

            builder.append("&6&l»&7 [" + entry.getKey() + "] ");
            builder.append(new NamedSourceFormat(entry.getValue()));
        }

        MessageUtils.message(source, builder::build);
    }

}
