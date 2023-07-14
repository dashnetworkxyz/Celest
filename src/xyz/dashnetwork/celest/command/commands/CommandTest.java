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
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.connection.User;

public final class CommandTest extends CelestCommand {

    public CommandTest() {
        super("test");

        setPermission(User::isOwner, true);
        addArguments(false, ArgumentType.INTEGER);
    }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6test1");
        builder.append(" test2").click(ClickEvent.openUrl("https://www.google.com"));
        builder.append(" test3").click(ClickEvent.openUrl("https://www.google.com")).hover("testy");

        builder.broadcast();
    }

}
