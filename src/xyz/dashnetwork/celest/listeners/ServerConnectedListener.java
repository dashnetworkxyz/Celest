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
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;

public final class ServerConnectedListener {

    @Subscribe
    public void onServerConnected(ServerConnectedEvent event) {
        Player player = event.getPlayer();
        RegisteredServer server = event.getServer();
        String name = server.getServerInfo().getName();

        player.sendPlayerListHeaderAndFooter(
                ComponentUtils.fromLegacyString("&6&lDashNetwork&7\nYou are connected to &6" + name + "\n"),
                ComponentUtils.fromLegacyString("&6\nplay.dashnetwork.xyz"));
    }

}
