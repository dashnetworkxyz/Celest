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
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.scheduler.Scheduler;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.utils.BrandUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.concurrent.TimeUnit;

public final class ServerPostConnectListener {

    private static final Celest plugin = Celest.getInstance();
    private static final Scheduler scheduler = Celest.getServer().getScheduler();
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

        if (player.getProtocolVersion().compareTo(ProtocolVersion.MINECRAFT_1_13) >= 0) {
            scheduler.buildTask(plugin, () ->
                    player.sendPluginMessage(brand, name)
            ).delay(1, TimeUnit.SECONDS).schedule();
        }
    }

}
