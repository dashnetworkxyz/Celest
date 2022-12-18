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
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

public final class DisconnectListener {

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        UserData data = user.getData();
        MessageBuilder builder = new MessageBuilder();

        if (data.getVanish()) {
            builder.append("&3&l»&r ");
            builder.append(new NamedSourceFormat(user));
            builder.append("&3 silently left.");

            MessageUtils.broadcast(each -> each.isStaff() || each.getData().getVanish(), builder::build);
        } else {
            builder.append("&c&l»&r ");
            builder.append(new NamedSourceFormat(user));
            builder.append("&c left.");

            MessageUtils.broadcast(builder::build);

            data.setLastPlayed(System.currentTimeMillis()); // TODO: Set this variable on /vanish
        }

        user.remove();
    }

}
