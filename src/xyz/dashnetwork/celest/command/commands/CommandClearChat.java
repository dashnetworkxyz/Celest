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
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;

import java.util.Optional;
import java.util.function.Predicate;

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
        Predicate<User> notSelf = user -> !user.getPlayer().equals(source);

        for (int i = 0; i < 99; i++)
            builder.append("\n");

        if (players.length > 1) {
            builder.append("&6&l»&7 Chat was cleared by ").onlyIf(notSelf);
            builder.append(new NamedSourceFormat(named)).onlyIf(notSelf);
            builder.append("&7.").onlyIf(notSelf);
        }

        for (Player player : players)
            MessageUtils.message(player, builder::build);

        builder = new MessageBuilder();
        builder.append("&6&l»&7 Chat was cleared for ");
        builder.append(new PlayerFormat(players));
        builder.append("&7.");

        MessageUtils.message(source, builder::build);
    }

}
