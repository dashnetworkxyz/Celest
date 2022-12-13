/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.inject.Injector;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;

public final class ServerPostConnectListener {

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    public void onServerPostConnect(ServerPostConnectEvent event) {
        Player player = event.getPlayer();
        Optional<ServerConnection> optional = player.getCurrentServer();
        User user = User.getUser(player);

        if (optional.isPresent()) {
            ServerConnection connection = optional.get();

            if (user.getData().getVanish())
                Channel.callOut("vanish", connection, user);

            Channel.callOut("displayname", connection, user);
        }

        if (player.getProtocolVersion().compareTo(ProtocolVersion.MINECRAFT_1_19) >= 0)
            Injector.injectSessionHandler(player);
    }

}
