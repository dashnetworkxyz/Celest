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
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.OfflineUserFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;
import xyz.dashnetwork.celest.utils.profile.OfflineUser;

import java.util.List;

public final class CommandStaffChat extends CelestCommand {

    public CommandStaffChat() {
        super("staffchat", "sc");

        setPermission(User::isStaff, true);
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
            offline.getData().setChannel(ChatChannel.STAFF);

            if (offline.isActive()) {
                User user = (User) offline;

                builder = new MessageBuilder();
                builder.append("&6&l»&7 You have been moved to &6StaffChat");

                if (!named.equals(user)) {
                    builder.append("&7 by &6");
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
            builder.append("&6&l»&7 You have moved &6");
            builder.append(new OfflineUserFormat(users, "&7, &6"));
            builder.append("&7 to &6StaffChat&7.");
            builder.message(source);
        }
    }

}
