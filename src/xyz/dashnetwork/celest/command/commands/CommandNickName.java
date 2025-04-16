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
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.PermissionType;
import xyz.dashnetwork.celest.chat.ColorUtil;
import xyz.dashnetwork.celest.chat.MessageUtil;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.formats.CommandSourceFormat;
import xyz.dashnetwork.celest.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.connection.User;

import java.util.List;

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
        MessageUtil.message(source, "&6&l»&7 \"&6/" + label + " off&7\" will clear your nickname.");
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
            int length = ColorUtil.strip(string).length();

            if (length < 4 || length > 16) {
                MessageUtil.message(source, "&6&l»&c Your nickname must be within 4-16 characters.");
                return;
            }
        }

        if (!ColorUtil.strip(string).matches("[A-z0-9]+") && !admin) {
            MessageUtil.message(source, "&6&l»&c Your nickname must be alphanumeric.");
            return;
        }

        if (off)
            string = null;
        else
            string = ColorUtil.fromAmpersand(string);

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
                builder.append(new CommandSourceFormat(named));
            }

            builder.append("&7.");
            builder.message(player);
        }

        if (source instanceof Player)
            players.remove(source);

        if (!players.isEmpty()) {
            builder = new MessageBuilder();
            builder.append("&6&l»&7 Nicknames for &6");
            builder.append(new PlayerFormat(players, "&7, &6"));

            if (off)
                builder.append("&7 have been cleared.");
            else
                builder.append("&7 have been set to &6" + string + "&7.");

            builder.message(source);
        }
    }

}
