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
import xyz.dashnetwork.celest.utils.chat.ChatChannel;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class CommandVanish extends CelestCommand {

    public CommandVanish() {
        super("vanish", "v");

        setPermission(User::isStaff, true);
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
        Predicate<User> predicate = each -> each.isStaff() || each.getData().getVanish();

        for (Player player : players) {
            User user = User.getUser(player);
            UserData data = user.getData();
            boolean vanish = !data.getVanish();

            data.setVanish(vanish);

            builder = new MessageBuilder();

            if (vanish) {
                builder.append("&c&l»&r ");
                builder.append(new NamedSourceFormat(user));
                builder.append("&c left.");

                data.setLastPlayed(System.currentTimeMillis());

                if (data.getChannel().equals(ChatChannel.GLOBAL)) {
                    data.setChannel(ChatChannel.STAFF);

                    MessageUtils.message(player, "&6&l»&7 You have been moved to &6StaffChat&7 because you vanished.");
                }

                on.add(player);
            } else {
                builder.append("&a&l»&r ");
                builder.append(new NamedSourceFormat(user));
                builder.append("&a joined.");

                off.add(player);
            }

            MessageUtils.broadcast(false, predicate.negate(), builder::build);

            xyz.dashnetwork.celest.channel.Channel.callOut("vanish", user);
        }

        if (on.size() > 0) {
            builder = new MessageBuilder();
            builder.append("&3&l»&r ");
            builder.append(new PlayerFormat(players));
            builder.append("&3 is now vanished. Poof.");

            MessageUtils.broadcast(predicate, builder::build);
        }

        if (off.size() > 0) {
            builder = new MessageBuilder();
            builder.append("&3&l»&r ");
            builder.append(new PlayerFormat(players));
            builder.append("&3 is no longer vanished.");

            MessageUtils.broadcast(predicate, builder::build);
        }
    }

}
