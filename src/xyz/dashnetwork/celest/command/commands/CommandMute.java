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
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerProfileFormat;
import xyz.dashnetwork.celest.utils.connection.OfflineUser;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;

import java.util.Optional;
import java.util.UUID;

public final class CommandMute extends CelestCommand {

    public CommandMute() {
        super("mute");

        setPermission(User::isAdmin, true);
        addArguments(ArgumentType.OFFLINE_USER);
        addArguments(ArgumentType.MESSAGE);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<OfflineUser> optional = arguments.get(OfflineUser.class);

        if (optional.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        OfflineUser offline = optional.get();
        String reason = arguments.get(String.class).orElse("No reason provided.");
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

            MessageUtils.message(((User) offline).getPlayer(), builder::build);
        }

        builder = new MessageBuilder();
        builder.append("&6&l»&r ");
        builder.append(new PlayerProfileFormat(offline.toPlayerProfile()));
        builder.append("&7 permanently muted by ");
        builder.append(new NamedSourceFormat(named));
        builder.append("&7.");
        builder.append("\n&6&l»&7 Hover here for details.")
                .hover("&7Judge: &6" + username
                        + "\n&7Reason: &6" + reason);

        MessageUtils.broadcast(User::isStaff, builder::build);
    }

}
