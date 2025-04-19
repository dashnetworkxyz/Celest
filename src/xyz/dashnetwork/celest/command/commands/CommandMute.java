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
import net.kyori.adventure.text.format.NamedTextColor;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.formats.CommandSourceFormat;
import xyz.dashnetwork.celest.chat.builder.formats.OfflineUserFormat;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.storage.data.PunishData;

import java.util.UUID;

public final class CommandMute extends CelestCommand {

    public CommandMute() {
        super("mute");

        setPermission(User::isAdmin, true);
        addArguments(true, ArgumentType.OFFLINE_USER);
        addArguments(false, ArgumentType.MULTI_STRING);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        OfflineUser offline = arguments.required(OfflineUser.class);
        String reason = arguments.optional(String.class).orElse("No reason provided.");
        UUID uuid = null;

        if (source instanceof Player player)
            uuid = player.getUniqueId();

        offline.getData().setMute(new PunishData(uuid, reason, null));

        NamedSource named = NamedSource.of(source);
        String username = named.getUsername();
        MessageBuilder builder;

        if (offline.isActive()) {
            builder = new MessageBuilder();
            builder.append("&6&l»&7 You have been permanently muted. Hover for more info.")
                    .hover("&7You were muted by &6" + username
                            + "\n\n" + reason);
            builder.message((User) offline);
        }

        builder = new MessageBuilder();
        builder.append("&6&l»&6 ");
        builder.append(new OfflineUserFormat(offline)).color(NamedTextColor.GOLD);
        builder.append("&7 permanently muted by ");
        builder.append(new CommandSourceFormat(named));
        builder.append("&7.");
        builder.append("\n&6&l»&7 Hover here for details.")
                .hover("&7Judge: &6" + username
                        + "\n&7Reason: &6" + reason);
        builder.broadcast(User::isStaff);
    }

}
