/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;

public final class ServerConnectedListener {

    @Subscribe
    public void onServerConnected(ServerConnectedEvent event) {
        Player player = event.getPlayer();
        String server = event.getServer().getServerInfo().getName();

        player.sendPlayerListHeaderAndFooter(
                ComponentUtils.toComponent("&6&lDashNetwork\n&7You are connected to &6" + server + "\n"),
                ComponentUtils.toComponent("\n&6play.dashnetwork.xyz"));
    }

}
