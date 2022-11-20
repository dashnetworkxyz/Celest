/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import xyz.dashnetwork.celest.inject.Injector;

public class ServerPostConnectListener {

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    public void onServerPostConnect(ServerPostConnectEvent event) {
        Injector.injectPlaySessionHandler(event.getPlayer());
    }

}
