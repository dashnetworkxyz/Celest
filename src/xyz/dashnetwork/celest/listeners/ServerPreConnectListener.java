/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.utils.User;

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
