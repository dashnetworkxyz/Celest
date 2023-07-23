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
import net.kyori.adventure.text.format.NamedTextColor;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.ColorUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerProfileFormat;
import xyz.dashnetwork.celest.utils.connection.User;

public final class CommandRealName extends CelestCommand {

    public CommandRealName() {
        super("realname");

        addArguments(true, ArgumentType.STRING);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        String search = arguments.required(String.class);
        MessageBuilder builder = new MessageBuilder();

        for (User user : User.getUsers()) {
            String nickname = user.getData().getNickName();

            if (nickname != null && ColorUtils.strip(nickname).toLowerCase().contains(search.toLowerCase())) {
                if (builder.size() > 0)
                    builder.append("\n");

                builder.append("&6&l»&7 ");
                builder.append(new NamedSourceFormat(user));
                builder.append("&7 real name is ");
                builder.append(new PlayerProfileFormat(user.toPlayerProfile())).color(NamedTextColor.GOLD);
                builder.append("&7.");
            }
        }

        if (builder.size() == 0)
            builder.append("&6&l»&7 No player found for &6" + search + "&7.");

        builder.message(source);
    }

}
