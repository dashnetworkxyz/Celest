/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.User;
import xyz.dashnetwork.celest.utils.PunishData;

public class PlayerChatListener {

    @Subscribe
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        PunishData mute = user.getData().getMute();

        if (mute != null) {
            long expiration = mute.getExpiration();

            if (expiration == -1 || expiration > System.currentTimeMillis()) {
                String reason = mute.getReason();
                // Component message = expiration == -1 ? // TODO: Username pulling
                //         Messages.playerMuted(reason) :
                //         Messages.playerMutedTemporary(reason, TimeUtils.toDate(expiration));
            }
        }
    }

}
