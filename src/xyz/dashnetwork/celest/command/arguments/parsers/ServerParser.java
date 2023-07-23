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

import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.arguments.Parser;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;

public final class ServerParser implements Parser {

    @Override
    public Object parse(User user, String input) {
        Optional<RegisteredServer> optional = Celest.getServer().getServer(input);

        if (optional.isEmpty())
            return null;
        
        RegisteredServer server = optional.get();
        String name = server.getServerInfo().getName().toLowerCase();

        if (user == null || user.isOwner() ||  user.getPlayer().hasPermission("dashnetwork.server." + name))
            return server;

        return null;
    }

}
