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
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

public final class PostLoginListener {

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        UserData data = user.getData();
        MessageBuilder builder = new MessageBuilder();

        if (data.getVanish()) {
            builder.append("&3&l»&r ");
            builder.append(new NamedSourceFormat(user));
            builder.append("&3 silently joined.");

            MessageUtils.broadcast(each -> each.isStaff() || each.getData().getVanish(), builder::build);
        } else if (data.getLastPlayed() == -1) {
            builder.append("&6&l»&6 Welcome, ");
            builder.append(new NamedSourceFormat(user));
            builder.append("&6, to &lDashNetwork");

            MessageUtils.broadcast(builder::build);
        } else {
            builder.append("&a&l»&r ");
            builder.append(new NamedSourceFormat(user));
            builder.append("&a joined.");

            MessageUtils.broadcast(builder::build);
        }

        user.getAddress().setServerPingTime(-1); // Reset for PingSpy

        // TODO: Altspy
    }

}
