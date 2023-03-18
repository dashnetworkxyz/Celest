/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
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
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.ArrayList;
import java.util.List;

public final class CommandSignSpy extends CelestCommand {

    public CommandSignSpy() {
        super("signspy");

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

        for (Player player : players) {
            User user = User.getUser(player);
            UserData data = user.getData();
            boolean spy = !data.getSignSpy();

            data.setSignSpy(spy);

            builder = new MessageBuilder();
            builder.append("&6&l»&7 You are ");

            if (spy) {
                builder.append("now");

                if (!source.equals(player))
                    on.add(player);
            } else {
                builder.append("no longer");

                if (!source.equals(player))
                    off.add(player);
            }

            builder.append(" in &6SignSpy");

            MessageUtils.message(player, builder::build);
        }

        int onSize = on.size();
        int offSize = off.size();

        if (onSize > 0) {
            builder = new MessageBuilder();
            builder.append("&6&l»&7 ");
            builder.append(new PlayerFormat(on));

            if (onSize > 1)
                builder.append("&7 are");
            else
                builder.append("&7 is");

            builder.append(" now in &6SignSpy");

            MessageUtils.message(source, builder::build);
        }

        if (offSize > 0) {
            builder = new MessageBuilder();
            builder.append("&6&l»&7 ");
            builder.append(new PlayerFormat(off));

            if (offSize > 1)
                builder.append("&7 are");
            else
                builder.append("&7 is");

            builder.append(" no longer in &6SignSpy");

            MessageUtils.message(source, builder::build);
        }
    }

}
