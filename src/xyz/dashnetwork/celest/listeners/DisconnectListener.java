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
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.Messages;
import xyz.dashnetwork.celest.utils.data.UserData;

public final class DisconnectListener {

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        UserData data = user.getData();

        String username = player.getUsername();
        String displayname = user.getDisplayname();

        if (data.getVanish())
            MessageUtils.broadcast(each -> each.isStaff() || each.getData().getVanish(),
                    Messages.leaveServerVanished(username, displayname));
        else {
            MessageUtils.broadcast(Messages.leaveServer(username, displayname));

            data.setLastPlayed(System.currentTimeMillis()); // TODO: Set this variable on /vanish
        }

        user.save(true);
        user.remove();
    }

}
