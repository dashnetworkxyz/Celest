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
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.inject.Injector;
import xyz.dashnetwork.celest.utils.BrandUtils;
import xyz.dashnetwork.celest.utils.connection.User;

public final class ServerPostConnectListener {

    private static final ChannelIdentifier brand = MinecraftChannelIdentifier.create("minecraft", "brand");
    private static final byte[] name = BrandUtils.toBytes("play.dashnetwork.xyz");

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    public void onServerPostConnect(ServerPostConnectEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        Channel.callOut("vanish", user);
        Channel.callOut("twofactor", user);
        Channel.callOut("displayname", user);

        ProtocolVersion version = player.getProtocolVersion();

        if (version.compareTo(ProtocolVersion.MINECRAFT_1_13) >= 0)
            player.sendPluginMessage(brand, name);

        // TODO: Fix SessionPlayerChat & SessionPlayerCommand (1.19.3)
        if (version.compareTo(ProtocolVersion.MINECRAFT_1_19) >= 0)
            Injector.injectPlayerKey(player);
    }

}
