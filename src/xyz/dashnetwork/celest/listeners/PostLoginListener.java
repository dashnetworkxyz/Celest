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
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.data.UserData;

public final class PostLoginListener {

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        UserData data = user.getData();
        Format format = new PlayerFormat(player);

        if (data.getVanish())
            MessageUtils.broadcast(each -> each.isStaff() || each.getData().getVanish(), each ->
                    Messages.joinServerVanished(each, format));
        else if (data.getLastPlayed() == -1)
            MessageUtils.broadcast(each -> Messages.welcome(each, format));
        else
            MessageUtils.broadcast(each -> Messages.joinServer(each, format));

        user.getAddress().setServerPingTime(-1); // Reset for PingSpy

        // TODO: Altspy
    }

}
