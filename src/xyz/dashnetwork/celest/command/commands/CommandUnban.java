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
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.formats.CommandSourceFormat;
import xyz.dashnetwork.celest.chat.builder.formats.OfflineUserFormat;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.connection.OfflineUser;
import xyz.dashnetwork.celest.storage.data.UserData;

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
        builder.append("&6&l»&6 ");
        builder.append(new OfflineUserFormat(offline)).color(NamedTextColor.GOLD);
        builder.append("&7 unbanned by ");
        builder.append(new CommandSourceFormat(NamedSource.of(source)));
        builder.append("&7.");
        builder.broadcast(User::isStaff);
    }

}
