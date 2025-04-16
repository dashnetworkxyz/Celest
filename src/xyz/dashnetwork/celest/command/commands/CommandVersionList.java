/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.network.ProtocolVersion;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.VersionUtil;
import xyz.dashnetwork.celest.chat.MessageUtil;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.formats.CommandSourceFormat;
import xyz.dashnetwork.celest.connection.User;

import java.util.*;

public final class CommandVersionList extends CelestCommand {

    public CommandVersionList() { super("versionlist", "verlist", "vlist"); }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Map<ProtocolVersion, List<NamedSource>> map = new TreeMap<>(ProtocolVersion::compareTo);
        Optional<User> optional = User.getUser(source);

        for (User each : User.getUsers()) {
            if (optional.map(u -> u.canSee(each)).orElse(true)) {
                ProtocolVersion version = each.getPlayer().getProtocolVersion();
                List<NamedSource> list = map.getOrDefault(version, new ArrayList<>());

                list.add(each);
                map.put(version, list);
            }
        }

        if (map.isEmpty()) {
            MessageUtil.message(source, "&6&l»&7 No players online.");
            return;
        }

        MessageBuilder builder = new MessageBuilder();

        for (Map.Entry<ProtocolVersion, List<NamedSource>> entry : map.entrySet()) {
            if (builder.size() > 0)
                builder.append("\n");

            builder.append("&6&l»&7 [&6" + VersionUtil.getVersionString(entry.getKey()) + "&7] &6");
            builder.append(new CommandSourceFormat(entry.getValue(), "&7, &6"));
        }

        builder.message(source);
    }

}
