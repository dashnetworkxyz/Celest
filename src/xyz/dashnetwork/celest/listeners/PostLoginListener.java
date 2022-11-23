/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.Messages;
import xyz.dashnetwork.celest.utils.data.UserData;

public final class PostLoginListener {

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        UserData data = user.getData();

        String username = player.getUsername();
        String displayname = user.getDisplayname();

        if (data.getVanish())
            MessageUtils.broadcast(each -> each.isStaff() || each.getData().getVanish(),
                    Messages.joinServerVanished(username, displayname));
        else if (data.getLastPlayed() == -1)
            MessageUtils.broadcast(Messages.welcome(username, displayname));
        else
            MessageUtils.broadcast(Messages.joinServer(username, displayname));

        user.getAddress().setServerPingTime(-1); // Reset for PingSpy

        // TODO: Altspy
    }

}
