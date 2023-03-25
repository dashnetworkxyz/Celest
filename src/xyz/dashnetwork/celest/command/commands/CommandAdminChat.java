/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.ChatChannel;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.OfflineUserFormat;
import xyz.dashnetwork.celest.utils.connection.OfflineUser;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;

import java.util.List;

public final class CommandAdminChat extends CelestCommand {

    public CommandAdminChat() {
        super("adminchat", "ac");

        setPermission(User::isAdmin, true);
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
        MessageBuilder builder;

        for (OfflineUser offline : users) {
            offline.getData().setChannel(ChatChannel.ADMIN);

            if (offline.isActive()) {
                User user = (User) offline;

                builder = new MessageBuilder();
                builder.append("&6&l»&7 You have been moved to &6AdminChat");

                if (!named.equals(user)) {
                    builder.append("&7 by ");
                    builder.append(new NamedSourceFormat(named));
                }

                builder.append("&7.");

                MessageUtils.message(user, builder::build);
            }
        }

        if (named instanceof User)
            users.remove(named);

        if (users.size() > 0) {
            builder = new MessageBuilder();
            builder.append("&6&l»&7 You have moved ");
            builder.append(new OfflineUserFormat(users));
            builder.append("&7 to &6AdminChat&7.");

            MessageUtils.message(source, builder::build);
        }
    }

}
