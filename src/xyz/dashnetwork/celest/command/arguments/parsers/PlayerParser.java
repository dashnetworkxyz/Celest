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
import com.velocitypowered.api.proxy.ProxyServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.arguments.Parser;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.connection.User;

import java.util.Optional;
import java.util.UUID;

public final class PlayerParser implements Parser {

    private static final ProxyServer server = Celest.getServer();

    @Override
    public Object parse(User user, String input) {
        if (input.matches("@[PpSs]") && user != null)
            return user.getPlayer();

        Optional<Player> optional;

        if (StringUtils.matchesUuid(input))
            optional = server.getPlayer(UUID.fromString(input));
        else
            optional = server.getPlayer(input);

        if (optional.isEmpty())
            return null;

        Player player = optional.get();
        User selected = User.getUser(player);

        if (user == null || user.canSee(selected))
            return player;

        return null;
    }

}
