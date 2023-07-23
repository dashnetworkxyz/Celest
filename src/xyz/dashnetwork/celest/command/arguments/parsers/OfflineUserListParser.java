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

import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Parser;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.OfflineUser;

import java.util.HashSet;
import java.util.Set;

public final class OfflineUserListParser implements Parser {

    @Override
    public Object parse(User user, String input) {
        Set<OfflineUser> set = new HashSet<>();

        for (String each : input.split(",")) {
            if (each.equalsIgnoreCase("@a")) {
                for (User online : User.getUsers())
                    if (user.canSee(online))
                        set.add(online);
            } else {
                Object offline = ArgumentType.OFFLINE_USER.parse(user, each);

                if (offline != null)
                    set.add((OfflineUser) offline);
            }
        }

        if (set.isEmpty())
            return null;

        return set.toArray(OfflineUser[]::new);
    }

}
