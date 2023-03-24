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
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;

import java.util.List;
import java.util.Optional;

public final class CommandChat extends CelestCommand {

    public CommandChat() {
        super("chat");

        addArguments(ArgumentType.CHANNEL);
        addArguments(User::isOwner, true, ArgumentType.PLAYER_LIST);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<ChatType> optional = arguments.get(ChatType.class);
        List<Player> players = arguments.playerListOrSelf(source);

        if (optional.isEmpty() || players.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        NamedSource named = NamedSource.of(source);
        ChatType chatType = optional.get();
        String name = chatType.getName();
        MessageBuilder builder;

        for (Player player : players) {
            User user = User.getUser(player);
            user.getData().setChatType(chatType);

            builder = new MessageBuilder();
            builder.append("&6&l»&7 You have been moved to &6" + name + "Chat");

            if (!source.equals(player)) {
                builder.append("&7 by ");
                builder.append(new NamedSourceFormat(named));
            }

            MessageUtils.message(player, builder::build);
        }

        if (source instanceof Player)
            players.remove(source);

        if (players.size() > 0) {
            builder = new MessageBuilder();
            builder.append("&6&l»&7 You have moved ");
            builder.append(new PlayerFormat(players));
            builder.append("&7 to &6" + name + "Chat");

            MessageUtils.message(source, builder::build);
        }
    }

}
