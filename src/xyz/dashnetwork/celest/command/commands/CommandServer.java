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
import com.velocitypowered.api.proxy.ConnectionRequestBuilder;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.LazyUtils;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.chat.Messages;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.Section;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;

import java.util.List;
import java.util.Optional;

public final class CommandServer extends CelestCommand {

    public CommandServer() {
        super("server");

        addArguments(true, ArgumentType.SERVER);
        addArguments(User::isOwner, true, ArgumentType.PLAYER_LIST);
    }

    @Override
    protected void sendUsage(@NotNull CommandSource source, @NotNull String label) {
        super.sendUsage(source, label);
        Messages.serverlistMessage(source);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        RegisteredServer server = arguments.required(RegisteredServer.class);
        List<Player> players = arguments.playerListOrSelf(source);

        if (players.isEmpty()) {
            sendUsage(source, label);
            return;
        }

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
            builder.message(player);

            player.createConnectionRequest(server).connect().thenAccept(u -> {
                if (LazyUtils.anyEquals(u.getStatus(),
                        ConnectionRequestBuilder.Status.CONNECTION_CANCELLED,
                        ConnectionRequestBuilder.Status.SERVER_DISCONNECTED)) {
                    Optional<Component> component = u.getReasonComponent();

                    MessageBuilder kick = new MessageBuilder();
                    Section section = kick.append(
                            "&6&l»&7 Failed to connect to &6" + name + "&7. Hover for more info.");

                    component.ifPresentOrElse(
                            c -> section.hover("&6" + ComponentUtils.toString(c)),
                            () -> section.hover("&7No kick message provided."));

                    kick.message(player);
                }
            });
        }

        if (source instanceof Player)
            players.remove(source);

        if (!players.isEmpty()) {
            builder = new MessageBuilder();
            builder.append("&6&l»&7 You have sent &6");
            builder.append(new PlayerFormat(players, "&7, &6"));
            builder.append("&7 to &6" + name + "&7.");
            builder.message(source);
        }
    }

}
