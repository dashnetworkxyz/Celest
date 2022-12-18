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
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.ProtocolVersionFormat;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.*;

public final class CommandVersionList extends CelestCommand {

    public CommandVersionList() { super("versionlist", "verlist"); }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Map<ProtocolVersion, List<NamedSource>> map = new HashMap<>();
        User user = CastUtils.toUser(source);

        for (User each : User.getUsers()) {
            if (user == null || user.canSee(each)) {
                ProtocolVersion version = each.getPlayer().getProtocolVersion();
                List<NamedSource> list = map.getOrDefault(version, new ArrayList<>());

                list.add(each);
                map.put(version, list);
            }
        }

        List<Map.Entry<ProtocolVersion, List<NamedSource>>> list = new ArrayList<>(map.entrySet());
        list.sort((entry1, entry2) -> entry2.getKey().getProtocol() - entry1.getKey().getProtocol());

        MessageBuilder builder = new MessageBuilder();

        for (Map.Entry<ProtocolVersion, List<NamedSource>> entry : list) {
            if (!builder.isEmpty())
                builder.append("\n");

            builder.append("&6&l»&7 [");
            builder.append(new ProtocolVersionFormat(entry.getKey())).prefix("&7");
            builder.append("&7] ");
            builder.append(new NamedSourceFormat(entry.getValue()));
        }

        MessageUtils.message(source, builder::build);
    }

}
