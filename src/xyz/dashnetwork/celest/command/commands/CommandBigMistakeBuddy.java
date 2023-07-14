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
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.connection.User;

public final class CommandBigMistakeBuddy extends CelestCommand {

    public CommandBigMistakeBuddy() {
        super("bigmistakebuddynowwehaveyourgamefiles", "bigmistakebuddy", "bmb");

        setPermission(User::isOwner, true);
        addArguments(true, ArgumentType.PLAYER_LIST);
    }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        Player[] players = arguments.required(Player[].class);
        Component title = ComponentUtils.fromString("&6Big mistake, buddy");
        Component subtitle = ComponentUtils.fromString("&cNow we have your game files");

        for (Player player : players) {
            player.sendTitlePart(TitlePart.SUBTITLE, subtitle);
            player.sendTitlePart(TitlePart.TITLE, title);
        }

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 Title sent to &6");
        builder.append(new PlayerFormat(players, "&7, &6"));
        builder.append("&7.");
        builder.message(source);
    }

}
