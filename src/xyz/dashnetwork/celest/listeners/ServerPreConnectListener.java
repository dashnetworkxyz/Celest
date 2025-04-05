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
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.utils.GrammarUtils;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.connection.User;

import java.util.Optional;

public final class ServerPreConnectListener {

    @Subscribe
    public void onServerPreConnect(ServerPreConnectEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        Optional<RegisteredServer> server = event.getResult().getServer();
        assert server.isPresent();

        String name = GrammarUtils.capitalization(server.get().getServerInfo().getName());
        String permission = "dashnetwork.server." + name.toLowerCase();

        if (!user.isOwner() && !player.hasPermission(permission)) {
            event.setResult(ServerPreConnectEvent.ServerResult.denied());

            MessageBuilder builder = new MessageBuilder();
            builder.append("&6&l»&7 ");
            builder.append(new PlayerFormat(player));
            builder.append("&7 was denied access to ");
            builder.append("&6" + name)
                    .hover("&7Click to copy &6" + permission)
                    .click(ClickEvent.suggestCommand(permission));
            builder.broadcast(each -> each.getData().getServerSpy());
        }
    }

}
