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

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;

public final class ServerPreConnectListener {

    @Subscribe
    public void onServerPreConnect(ServerPreConnectEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        Optional<RegisteredServer> server = event.getResult().getServer();
        assert server.isPresent();

        String name = server.get().getServerInfo().getName().toLowerCase();

        if (!user.isOwner() && !player.hasPermission("dashnetwork.server." + name))
            event.setResult(ServerPreConnectEvent.ServerResult.denied());
    }

}
