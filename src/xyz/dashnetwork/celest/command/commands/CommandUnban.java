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
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerProfileFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;
import xyz.dashnetwork.celest.utils.profile.OfflineUser;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

public final class CommandUnban extends CelestCommand {

    public CommandUnban() {
        super("unban");

        setPermission(User::isAdmin, true);
        addArguments(true, ArgumentType.OFFLINE_USER);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        OfflineUser offline = arguments.required(OfflineUser.class);
        UserData data = offline.getData();

        if (data != null)
            data.setBan(null);

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&f ");
        builder.append(new PlayerProfileFormat(offline.toPlayerProfile()));
        builder.append("&7 unbanned by ");
        builder.append(new NamedSourceFormat(NamedSource.of(source)));
        builder.append("&7.");
        builder.broadcast(User::isStaff);
    }

}
