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
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;

public final class CommandSudo extends CelestCommand {

    public CommandSudo() {
        super("sudo");

        setPermission(User::isOwner, true);
        addArguments(true, ArgumentType.PLAYER, ArgumentType.MULTI_STRING);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Player player = arguments.required(Player.class);
        String message = arguments.required(String.class);

        player.spoofChatInput(message);

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 Spoofed ");
        builder.append(new PlayerFormat(player));
        builder.append("&7 to say &6" + message + "&7.");
        builder.message(source);
    }

}
