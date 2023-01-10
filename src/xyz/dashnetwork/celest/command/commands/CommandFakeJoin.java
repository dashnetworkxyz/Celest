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
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;

public final class CommandFakeJoin extends CelestCommand {

    public CommandFakeJoin() {
        super("fakejoin");

        setPermission(User::isOwner, true);
        addArguments(ArgumentType.STRING, ArgumentType.MESSAGE);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<String> optional1 = arguments.get(String.class);
        Optional<String> optional2 = arguments.get(String.class);

        if (optional1.isEmpty() || optional2.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        String username = optional1.get();
        String displayname = optional2.get();

        MessageBuilder builder = new MessageBuilder();
        builder.append("&a&l»&r ");
        builder.append(displayname).hover("&6" + username);
        builder.append("&a joined.");

        MessageUtils.broadcast(each -> !each.isStaff(), builder::build);

        builder = new MessageBuilder();
        builder.append("&6&l»&r ");
        builder.append(displayname).hover("&6" + username);
        builder.append("&7 fake-joined by ");
        builder.append(new NamedSourceFormat(NamedSource.of(source)));

        MessageUtils.broadcast(User::isStaff, builder::build);
    }

}
