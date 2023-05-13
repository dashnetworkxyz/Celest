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
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.PermissionType;
import xyz.dashnetwork.celest.utils.chat.ColorUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;

import java.util.List;
import java.util.Optional;

public final class CommandNickName extends CelestCommand {

    public CommandNickName() {
        super("nickname", "nick");

        addArguments(true, ArgumentType.STRING);
        addArguments(User::isAdmin, true, ArgumentType.PLAYER_LIST);
        setSuggestions(0, "off");
    }

    @Override
    protected void sendUsage(@NotNull CommandSource source, @NotNull String label) {
        super.sendUsage(source, label);
        MessageUtils.message(source, "&6&l»&7 \"&6/" + label + " off&7\" will clear your nickname.");
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        List<Player> players = arguments.playerListOrSelf(source);

        if (players.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        String string = arguments.required(String.class);
        boolean off = string.equals("off");
        boolean admin = PermissionType.ADMIN.hasPermission(source);

        if (!off && !admin) {
            int length = ColorUtils.strip(string).length();

            if (length < 4 || length > 16) {
                MessageUtils.message(source, "&6&l»&c Your nickname must be within 4-16 characters.");
                return;
            }
        }

        if (!ColorUtils.strip(string).matches("[A-z0-9]+") && !admin) {
            MessageUtils.message(source, "&6&l»&c Your nickname must be alphanumeric.");
            return;
        }

        if (off)
            string = null;
        else
            string = ColorUtils.fromAmpersand(string);

        NamedSource named = NamedSource.of(source);
        MessageBuilder builder;

        for (Player player : players) {
            User user = User.getUser(player);
            user.getData().setNickName(string);

            builder = new MessageBuilder();

            if (off)
                builder.append("&6&l»&7 Your nickname has been cleared");
            else
                builder.append("&6&l»&7 Your nickname has been set to &6" + string);

            if (!source.equals(player)) {
                builder.append("&7 by ");
                builder.append(new NamedSourceFormat(named));
            }

            builder.append("&7.");

            MessageUtils.message(player, builder::build);
        }

        if (source instanceof Player) {
            players.remove(source);

            if (players.size() > 0) {
                builder = new MessageBuilder();
                builder.append("&6&l»&7 Nicknames for ");
                builder.append(new PlayerFormat(players));

                if (off)
                    builder.append("&7 have been cleared.");
                else
                    builder.append("&7 have been set to &6" + string + "&7.");

                MessageUtils.message(source, builder::build);
            }
        }
    }

}
