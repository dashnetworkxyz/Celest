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
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.chat.MessageUtils;
import xyz.dashnetwork.celest.chat.builder.PageBuilder;
import xyz.dashnetwork.celest.connection.User;

import java.util.Optional;

public final class CommandPage extends CelestCommand {

    public CommandPage() {
        super("page");

        setPermission(user -> true, false);
        addArguments(true, ArgumentType.INTEGER);
    }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        int page = arguments.required(Integer.class);
        Optional<User> optionalUser = User.getUser(source);
        assert optionalUser.isPresent();

        User user = optionalUser.get();
        PageBuilder message = user.getPageBuilder();

        if (message == null) {
            MessageUtils.message(source, "&6&l»&7 You have no pages to view!");
            return;
        }

        MessageUtils.message(source, message.build(user, page));
    }

}
