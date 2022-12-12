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
import xyz.dashnetwork.celest.utils.PunishUtils;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.utils.chat.Messages;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.ProfileUtils;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;

public final class LoginListener {

    @Subscribe
    public void onLogin(LoginEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        PunishData ban = user.getBan();

        if (!PunishUtils.isValid(ban))
            ban = user.getAddress().getData().getBan();

        if (PunishUtils.isValid(ban)) {
            long expiration = ban.getExpiration();
            String reason = ban.getReason();
            String banner = ProfileUtils.fromUuid(ban.getJudge()).getUsername();
            String date = TimeUtils.longToDate(expiration);

            Component message = expiration == -1 ?
                    Messages.loginBanned(reason, banner) :
                    Messages.loginBannedTemporary(reason, banner, date);

            event.setResult(ResultedEvent.ComponentResult.denied(message));
            user.remove();
        }

        // TODO: RealJoin. Will need injection.
    }

}
