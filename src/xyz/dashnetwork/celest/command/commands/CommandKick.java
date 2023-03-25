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
import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;

import java.util.List;

public final class CommandKick extends CelestCommand {

    public CommandKick() {
        super("kick");

        setPermission(User::isAdmin, true);
        addArguments(ArgumentType.PLAYER_LIST);
        addArguments(ArgumentType.MESSAGE);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        List<Player> players = arguments.playerListOrSelf(source);

        if (players.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        NamedSource named = NamedSource.of(source);
        String reason = arguments.get(String.class).orElse("No reason provided.");

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&lDashNetwork");
        builder.append("\n&7You have been kicked");
        builder.append("\n&7You were kicked by &6" + named.getUsername());
        builder.append("\n\n" + reason);

        Component message = builder.build(null);

        for (Player player : players)
            player.disconnect(message);

        builder = new MessageBuilder();
        builder.append("&6&l»&6");
        builder.append(new PlayerFormat(players));
        builder.append("&7 kicked by ");
        builder.append(new NamedSourceFormat(named));
        builder.append("&7.");

        MessageUtils.broadcast(builder::build);
    }

}
