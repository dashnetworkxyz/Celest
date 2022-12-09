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
import xyz.dashnetwork.celest.inject.Injector;

public final class ServerPostConnectListener {

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    public void onServerPostConnect(ServerPostConnectEvent event) {
        Player player = event.getPlayer();

        if (player.getProtocolVersion().compareTo(ProtocolVersion.MINECRAFT_1_19) >= 0)
            Injector.injectSessionHandler(player);
    }

}
