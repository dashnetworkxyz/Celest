/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.User;
import xyz.dashnetwork.celest.utils.MessageUtils;
import xyz.dashnetwork.celest.utils.Messages;

public class DisconnectListener {

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        String username = player.getUsername();
        String displayname = user.getDisplayname();

        if (user.getData().getVanish())
            MessageUtils.broadcast(User::isStaff, Messages.leaveServerVanished(username, displayname));
        else
            MessageUtils.broadcast(Messages.leaveServer(username, displayname));

        user.getData().setLastPlayed(System.currentTimeMillis());

        user.save();
        user.remove();
    }

}
