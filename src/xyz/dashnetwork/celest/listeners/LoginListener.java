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
import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.User;
import xyz.dashnetwork.celest.utils.*;

public final class LoginListener {

    @Subscribe
    public void onLogin(LoginEvent event) {
        User user = User.getUser(event.getPlayer());
        PunishData ban = user.getBan();

        if (PunishUtils.isValid(ban)) {
            long expiration = ban.getExpiration();
            String reason = ban.getReason();
            String banner = ProfileUtils.fromUuid(ban.getBanner()).getUsername();
            String date = TimeUtils.longToDate(expiration);

            Component message = expiration == -1 ?
                    Messages.loginBanned(reason, banner) :
                    Messages.loginBannedTemporary(reason, banner, date);

            event.setResult(ResultedEvent.ComponentResult.denied(message));
            user.remove();
        }
    }

}
