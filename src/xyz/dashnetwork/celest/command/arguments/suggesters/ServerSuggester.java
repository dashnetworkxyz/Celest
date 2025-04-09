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

package xyz.dashnetwork.celest.command.arguments.suggesters;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.arguments.Suggester;
import xyz.dashnetwork.celest.utils.GrammarUtil;
import xyz.dashnetwork.celest.utils.ListUtil;
import xyz.dashnetwork.celest.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class ServerSuggester implements Suggester {

    @Override
    public List<String> suggest(User user, String input) {
        List<String> list = new ArrayList<>();

        for (RegisteredServer server : Celest.getServer().getAllServers()) {
            String name = GrammarUtil.capitalization(server.getServerInfo().getName());

            if (user.isOwner() || user.getPlayer().hasPermission("dashnetwork.server." + name.toLowerCase()))
                ListUtil.addIfStarts(list, input, name);
        }

        return list;
    }

}
