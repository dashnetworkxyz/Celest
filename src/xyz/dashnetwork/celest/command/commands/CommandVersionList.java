/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.network.ProtocolVersion;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.CastUtils;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.VersionUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class CommandVersionList extends CelestCommand {

    public CommandVersionList() { super("versionlist", "verlist"); }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Map<ProtocolVersion, List<NamedSource>> map = new TreeMap<>(ProtocolVersion::compareTo);
        User user = CastUtils.toUser(source);

        for (User each : User.getUsers()) {
            if (user == null || user.canSee(each)) {
                ProtocolVersion version = each.getPlayer().getProtocolVersion();
                List<NamedSource> list = map.getOrDefault(version, new ArrayList<>());

                list.add(each);
                map.put(version, list);
            }
        }

        if (map.isEmpty()) {
            MessageUtils.message(source, "&6&l»&7 No players online.");
            return;
        }

        MessageBuilder builder = new MessageBuilder();

        for (Map.Entry<ProtocolVersion, List<NamedSource>> entry : map.entrySet()) {
            if (builder.length() > 0)
                builder.append("\n");

            builder.append("&6&l»&7 [" + VersionUtils.getVersionString(entry.getKey()) + "] ");
            builder.append(new NamedSourceFormat(entry.getValue()));
        }

        MessageUtils.message(source, builder::build);
    }

}
