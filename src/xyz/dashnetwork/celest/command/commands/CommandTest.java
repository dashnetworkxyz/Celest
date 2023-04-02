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

public final class CommandTest extends CelestCommand {

    public CommandTest() {
        super("test");

        setPermission(User::isOwner, true);
        addArguments(ArgumentType.INTEGER);
    }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        int page = arguments.get(Integer.class).orElse(1);

        MessageUtils.message(source, "Page: " + page);

        PageBuilder builder = new PageBuilder();
        builder.append("1");
        builder.append("2");
        builder.append("3");
        builder.append("4");
        builder.append("5");
        builder.append("6");
        builder.append("7");
        builder.append("8");
        builder.append("9");
        builder.append("10");
        builder.append("11");
        builder.append("12");
        builder.append("13");
        builder.append("14");
        builder.append("15");
        builder.append("16");
        builder.append("17");
        builder.append("18");
        builder.append("19");
        builder.append("20");
        builder.append("21");

        MessageUtils.message(source, builder.build(User.getUser(source).orElse(null), page));
    }

}
