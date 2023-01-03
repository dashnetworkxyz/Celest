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

package xyz.dashnetwork.celest.command.arguments.parser.parsers;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.parser.Parser;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class PlayerListParser implements Parser {

    @Override
    public Object parse(User user, String input) {
        List<Player> list = new ArrayList<>();

        if (input.equalsIgnoreCase("@a")) {
            for (User each : User.getUsers())
                if (user == null || user.canSee(each))
                    list.add(each.getPlayer());
        } else {
            for (String each : input.split(",")) {
                Object player = ArgumentType.PLAYER.parse(user, each);

                if (player != null)
                    list.add((Player) player);
            }
        }

        if (list.isEmpty())
            return null;

        return list.toArray(Player[]::new);
    }

}
