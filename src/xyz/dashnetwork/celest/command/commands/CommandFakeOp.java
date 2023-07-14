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
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class CommandFakeOp extends CelestCommand {

    public CommandFakeOp() {
        super("fakeop");

        setPermission(User::isOwner, true);
        addArguments(true, ArgumentType.PLAYER_LIST);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        String username = source instanceof Player player ? player.getUsername() : "Server";
        List<Player> players = new ArrayList<>(List.of(arguments.required(Player[].class)));
        MessageBuilder builder;

        for (Player player : players) {
            builder = new MessageBuilder();
            builder.append("&7&o[" + username + ": Opped " + player.getUsername() + "]");
            builder.message(player);
        }

        if (source instanceof Player)
            players.remove(source);

        if (players.size() > 0) {
            builder = new MessageBuilder();
            builder.append("&6&l»&7 Fake-op sent to &6");
            builder.append(new PlayerFormat(players, "&7, &6"));
            builder.append("&7.");
            builder.message(source);
        }
    }

}
