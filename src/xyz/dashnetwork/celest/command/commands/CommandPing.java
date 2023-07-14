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
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;

public final class CommandPing extends CelestCommand {

    public CommandPing() {
        super("ping", "p");

        addArguments(false, ArgumentType.PLAYER);
    }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        Player selected = arguments.playerOrSelf(source);

        if (selected == null) {
            sendUsage(source, label);
            return;
        }

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 ");
        builder.append(new PlayerFormat(selected));
        builder.append("&7 ping: &6" + selected.getPing() + "ms");
        builder.message(source);
    }

}
