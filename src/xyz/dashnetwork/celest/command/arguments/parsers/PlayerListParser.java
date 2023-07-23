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

package xyz.dashnetwork.celest.command.arguments.parsers;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Parser;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.HashSet;
import java.util.Set;

public final class PlayerListParser implements Parser {

    @Override
    public Object parse(User user, String input) {
        Set<Player> set = new HashSet<>();

        if (input.equalsIgnoreCase("@a")) {
            for (User each : User.getUsers())
                if (user == null || user.canSee(each))
                    set.add(each.getPlayer());
        } else {
            for (String each : input.split(",")) {
                Object player = ArgumentType.PLAYER.parse(user, each);

                if (player != null)
                    set.add((Player) player);
            }
        }

        if (set.isEmpty())
            return null;

        return set.toArray(Player[]::new);
    }

}
