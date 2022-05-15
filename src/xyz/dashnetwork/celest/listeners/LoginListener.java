/*
 * Copyright (c) 2022. Andrew Bell.
 * All rights reserved.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.User;

public class LoginListener {

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        Player player = event.getPlayer();
        User user = new User(player); // Create User instance on join.
    }

}
