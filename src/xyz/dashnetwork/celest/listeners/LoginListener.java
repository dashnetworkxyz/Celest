/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.User;
import xyz.dashnetwork.celest.utils.Messages;
import xyz.dashnetwork.celest.utils.PunishData;
import xyz.dashnetwork.celest.utils.UserData;

public class LoginListener {

    @Subscribe
    public void onLogin(LoginEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        UserData data = user.getData();
        PunishData ban = data.getBan();

        if (ban != null) {
            long expiration = ban.getExpiration();

            if (expiration == -1 || expiration > System.currentTimeMillis()) {
                String reason = ban.getReason();
                Component message = expiration == -1 ?
                        Messages.loginBanned(reason) :
                        Messages.loginBannedTemporary(reason, "TODO"); // TODO

                event.setResult(ResultedEvent.ComponentResult.denied(message));
            }
        }

        // TODO: Ban detection from Punish system
    }

}
