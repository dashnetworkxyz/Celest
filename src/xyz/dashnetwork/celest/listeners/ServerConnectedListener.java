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
import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.connection.User;

public final class ServerConnectedListener {

    @Subscribe
    public void onServerConnected(ServerConnectedEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        RegisteredServer server = event.getServer();
        String name = server.getServerInfo().getName();

        Channel.callOut("vanish", server, user);
        Channel.callOut("displayname", server, user);

        player.sendPlayerListHeaderAndFooter(
                ComponentUtils.fromLegacyString("&6&lDashNetwork&7\nYou are connected to &6" + name + "\n"),
                ComponentUtils.fromLegacyString("&6\nplay.dashnetwork.xyz"));
    }

}
