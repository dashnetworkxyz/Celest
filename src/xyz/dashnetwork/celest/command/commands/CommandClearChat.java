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
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.formats.CommandSourceFormat;
import xyz.dashnetwork.celest.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.connection.User;

public final class CommandClearChat extends CelestCommand {

    public CommandClearChat() {
        super("clearchat", "cc");

        setPermission(User::isStaff, true);
        addArguments(true, ArgumentType.PLAYER_LIST);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        NamedSource named = NamedSource.of(source);
        Player[] players = arguments.required(Player[].class);
        MessageBuilder builder = new MessageBuilder();

        for (int i = 0; i < 99; i++)
            builder.append("\n");

        if (players.length > 1) {
            builder.append("&6&l»&7 Chat was cleared by &6");
            builder.append(new CommandSourceFormat(named));
            builder.append("&7.");
        }

        for (Player player : players)
            if (!player.equals(source))
                builder.message(player);

        builder = new MessageBuilder();
        builder.append("&6&l»&7 Chat was cleared for &6");
        builder.append(new PlayerFormat(players, "&7, &6"));
        builder.append("&7.");
        builder.message(source);
    }

}
