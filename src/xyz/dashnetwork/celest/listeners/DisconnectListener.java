/*
 * Copyright (c) 2022. Andrew Bell.
 * All rights reserved.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.User;

public class DisconnectListener {

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        assert user != null; // Avoid compilation warnings

        user.save();
        user.remove();
    }

}
