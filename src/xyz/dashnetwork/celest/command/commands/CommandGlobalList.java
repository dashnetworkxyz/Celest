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
import com.velocitypowered.api.proxy.ServerConnection;
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.GrammarUtil;
import xyz.dashnetwork.celest.chat.MessageUtil;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.profile.NamedSource;

import java.util.*;

public final class CommandGlobalList extends CelestCommand {

    public CommandGlobalList() { super("globallist", "glist", "list"); }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Map<String, List<NamedSource>> map = new TreeMap<>(String::compareTo);
        int size = 0;
        Optional<User> user = User.getUser(source);

        for (User each : User.getUsers()) {
            if (user.map(u -> u.canSee(each)).orElse(true)) {
                Optional<ServerConnection> optional = each.getPlayer().getCurrentServer();

                if (optional.isEmpty())
                    continue;

                String name = GrammarUtil.capitalization(optional.get().getServerInfo().getName());
                List<NamedSource> list = map.getOrDefault(name, new ArrayList<>());

                list.add(each);
                map.put(name, list);
                size++;
            }
        }

        if (map.isEmpty()) {
            MessageUtil.message(source, "&6&l»&7 No players online.");
            return;
        }

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&6 " + size + "&7 players online.");

        for (Map.Entry<String, List<NamedSource>> entry : map.entrySet()) {
            builder.append("\n");

            String name = entry.getKey();

            builder.append("&6&l»&7 [");
            builder.append("&6" + name)
                    .hover("&7Click to copy &6/server " + name)
                    .click(ClickEvent.suggestCommand("/server " + name));
            builder.append("&7] &6");
            builder.append(new NamedSourceFormat(entry.getValue(), "&7, &6"));
        }

        builder.message(source);
    }

}
