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
import net.kyori.adventure.text.format.NamedTextColor;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.ChatChannel;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.OfflineUserFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;
import xyz.dashnetwork.celest.utils.profile.OfflineUser;

import java.util.List;

public final class CommandChat extends CelestCommand {

    public CommandChat() {
        super("chat");

        addArguments(true, ArgumentType.CHANNEL);
        addArguments(User::isOwner, true, ArgumentType.OFFLINE_USER_LIST);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        List<OfflineUser> users = arguments.offlineListOrSelf(source);

        if (users.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        NamedSource named = NamedSource.of(source);
        ChatChannel channel = arguments.required(ChatChannel.class);
        String name = channel.getName();
        MessageBuilder builder;

        for (OfflineUser offline : users) {
            offline.getData().setChannel(channel);

            if (offline.isActive()) {
                User user = (User) offline;

                builder = new MessageBuilder();
                builder.append("&6&l»&7 You have been moved to &6" + name + "Chat");

                if (!named.equals(user)) {
                    builder.append("&7 by ");
                    builder.append(new NamedSourceFormat(named));
                }

                builder.append("&7.");
                builder.message(user);
            }
        }

        if (named instanceof User)
            users.remove(named);

        if (users.size() > 0) {
            builder = new MessageBuilder();
            builder.append("&6&l»&7 You have moved ");
            builder.append(new OfflineUserFormat(users, "&7, ")).color(NamedTextColor.GOLD);
            builder.append("&7 to &6" + name + "Chat&7.");
            builder.message(source);
        }
    }

}
