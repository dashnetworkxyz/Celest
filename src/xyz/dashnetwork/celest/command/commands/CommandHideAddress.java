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
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.sql.data.UserData;

import java.util.ArrayList;
import java.util.List;

public final class CommandHideAddress extends CelestCommand {

    public CommandHideAddress() {
        super("hideaddress", "streamer");

        setPermission(User::isAdmin, true);
        addArguments(User::isOwner, true, ArgumentType.PLAYER_LIST);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        List<Player> players = arguments.playerListOrSelf(source);

        if (players.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        List<Player> on = new ArrayList<>();
        List<Player> off = new ArrayList<>();
        MessageBuilder builder;

        for (Player player : players) {
            User user = User.getUser(player);
            UserData data = user.getData();
            boolean hidden = !data.getHideAddress();

            data.setHideAddress(hidden);

            builder = new MessageBuilder();
            builder.append("&6&l»&7 You will ");

            if (hidden) {
                builder.append("no longer");

                if (!source.equals(player))
                    on.add(player);
            } else {
                builder.append("now");

                if (!source.equals(player))
                    off.add(player);
            }

            builder.append(" see &6IP Addresses&7.");
            builder.message(player);

            Channel.callOut("userdata", user);
        }

        int onSize = on.size();
        int offSize = off.size();

        if (onSize > 0) {
            builder = new MessageBuilder();
            builder.append("&6&l»&6 ");
            builder.append(new PlayerFormat(on, "&7, &6"));
            builder.append(" will no longer see &6IP Addresses&7.");
            builder.message(source);
        }

        if (offSize > 0) {
            builder = new MessageBuilder();
            builder.append("&6&l»&6 ");
            builder.append(new PlayerFormat(on, "&7, &6"));
            builder.append(" will now see &6IP Addresses&7.");
            builder.message(source);
        }
    }

}
