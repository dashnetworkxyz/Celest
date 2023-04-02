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
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.PageBuilder;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;

public final class CommandPage extends CelestCommand {

    public CommandPage() {
        super("page");

        setPermission(user -> true, false);
        addArguments(ArgumentType.INTEGER);
    }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        Optional<Integer> optionalInteger = arguments.get(Integer.class);

        if (optionalInteger.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        Optional<User> optionalUser = User.getUser(source);
        assert optionalUser.isPresent();

        User user = optionalUser.get();
        PageBuilder message = user.getPagedMessage();

        if (message == null) {
            MessageUtils.message(source, "&6&l»&7 You have no pages to view!");
            return;
        }

        MessageUtils.message(source, message.build(user, optionalInteger.get()));
    }

}
