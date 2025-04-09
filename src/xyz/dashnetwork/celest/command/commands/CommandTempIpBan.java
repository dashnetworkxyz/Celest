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
import xyz.dashnetwork.celest.utils.TimeUtil;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.connection.Address;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.profile.NamedSource;
import xyz.dashnetwork.celest.storage.data.PunishData;

import java.util.UUID;

public final class CommandTempIpBan extends CelestCommand {

    public CommandTempIpBan() {
        super("tempipban", "tempbanip");

        setPermission(User::isAdmin, true);
        addArguments(true, ArgumentType.ADDRESS, ArgumentType.LONG);
        addArguments(false, ArgumentType.MULTI_STRING);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Address address = arguments.required(Address.class);
        long duration = arguments.required(Long.class);
        String reason = arguments.optional(String.class).orElse("No reason provided.");
        String date = TimeUtil.longToDate(System.currentTimeMillis() + duration);
        UUID uuid = null;

        if (source instanceof Player player)
            uuid = player.getUniqueId();

        address.getData().setBan(new PunishData(uuid, reason, duration));

        NamedSource named = NamedSource.of(source);
        String username = named.getUsername();
        MessageBuilder builder;

        for (User user : User.getUsers()) {
            if (user.getAddress().equals(address)) {
                builder = new MessageBuilder();
                builder.append("&6&lDashNetwork");
                builder.append("\n&7You have been temporarily banned");
                builder.append("\n&7You were banned by &6" + username);
                builder.append("\n&7Your ban will expire on &6" + date);
                builder.append("\n\n" + reason);

                user.getPlayer().disconnect(builder.build(null));
            }
        }

        builder = new MessageBuilder();
        builder.append("&6&l»&6 " + address.getString() + "&7 temporarily banned by ");
        builder.append(new NamedSourceFormat(named));
        builder.append("&7.");
        builder.append("\n&6&l»&7 Hover here for details.")
                .hover("&7Judge: &6" + username
                        + "\n&7Expiration: &6" + date
                        + "\n&7Reason: &6" + reason);
        builder.broadcast(User::isStaff);
    }

}
