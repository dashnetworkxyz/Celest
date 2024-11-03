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

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.utils.GrammarUtils;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;

public final class ServerConnectedListener {

    @Subscribe
    public void onServerConnected(ServerConnectedEvent event) {
        Player player = event.getPlayer();
        RegisteredServer server = event.getServer();
        String name = GrammarUtils.capitalization(server.getServerInfo().getName());

        player.sendPlayerListHeaderAndFooter(
                ComponentUtils.fromString("&6&lDashNetwork&7\nYou are connected to &6" + GrammarUtils.capitalization(name) + "\n"),
                ComponentUtils.fromString("&6\nplay.dashnetwork.xyz")
        );

        if (event.getPreviousServer().isPresent()) {
            MessageBuilder builder = new MessageBuilder();
            builder.append("&6&l»&7 ");
            builder.append(new PlayerFormat(player));
            builder.append("&7 has connected to ");
            builder.append("&6" + name)
                    .hover("&7Click to copy &6/server " + name)
                    .click(ClickEvent.suggestCommand("/server " + name));
            builder.append("&7.");
            builder.broadcast(each -> each.getData().getServerSpy());
        }
    }

}
