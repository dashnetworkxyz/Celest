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
import com.velocitypowered.api.proxy.ConnectionRequestBuilder;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.LazyUtils;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;

import java.util.List;
import java.util.Optional;

public final class CommandSurvival extends CelestCommand {

    private static final ProxyServer proxy = Celest.getServer();

    public CommandSurvival() {
        super("survival");

        addArguments(User::isOwner, true, ArgumentType.PLAYER_LIST);
    }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        Optional<RegisteredServer> optional = proxy.getServer("survival");
        List<Player> players = arguments.playerListOrSelf(source);

        if (optional.isEmpty()) {
            MessageUtils.message(source, "&6&l»&7 No server for &6Survival&7 was found.");
            return;
        }

        if (players.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        RegisteredServer server = optional.get();
        String name = server.getServerInfo().getName();
        NamedSource named = NamedSource.of(source);
        MessageBuilder builder;

        for (Player player : players) {
            builder = new MessageBuilder();
            builder.append("&6&l»&7 You have been sent to &6" + name);

            if (!source.equals(player)) {
                builder.append("&7 by ");
                builder.append(new NamedSourceFormat(named));
            }

            builder.append("&7.");

            MessageUtils.message(player, builder::build);

            player.createConnectionRequest(server).connect().thenAccept(u -> {
                if (LazyUtils.anyEquals(u.getStatus(),
                        ConnectionRequestBuilder.Status.CONNECTION_CANCELLED,
                        ConnectionRequestBuilder.Status.SERVER_DISCONNECTED)) {
                    Optional<Component> component = u.getReasonComponent();

                    MessageBuilder kick = new MessageBuilder();
                    TextSection section = kick.append(
                            "&6&l»&7 Failed to connect to &6" + name + "&7. Hover for more info.");

                    component.ifPresentOrElse(
                            c -> section.hover("&6" + ComponentUtils.toString(c)),
                            () -> section.hover("&7No kick message provided."));

                    MessageUtils.message(player, kick::build);
                }
            });
        }

        if (source instanceof Player)
            players.remove(source);

        if (players.size() > 0) {
            builder = new MessageBuilder();
            builder.append("&6&l»&7 You have sent ");
            builder.append(new PlayerFormat(players));
            builder.append("&7 to &6" + name + "&7.");

            MessageUtils.message(source, builder::build);
        }
    }

}