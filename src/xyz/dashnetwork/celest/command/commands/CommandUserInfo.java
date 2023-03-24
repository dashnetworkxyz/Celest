/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
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
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.connection.OfflineUser;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;

public final class CommandUserInfo extends CelestCommand {

    public CommandUserInfo() {
        super("userinfo", "playerinfo");

        setPermission(User::isAdmin, true);
        addArguments(ArgumentType.OFFLINE_USER);
    }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        Optional<OfflineUser> optional = arguments.get(OfflineUser.class);

        if (optional.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        OfflineUser offline = optional.get();
        MessageBuilder builder = new MessageBuilder();

        builder.append("&6&l»&7 "); // TODO

    }

}
