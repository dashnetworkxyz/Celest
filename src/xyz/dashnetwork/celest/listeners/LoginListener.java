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
import xyz.dashnetwork.celest.utils.PunishUtils;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
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
            String type = expiration == -1 ? "permanently" : "temporarily";

            MessageBuilder builder = new MessageBuilder();
            builder.append("&6&lDashNetwork");
            builder.append("\n&7You have been " + type + " banned");
            builder.append("\n&7You were banned by &6" + ProfileUtils.fromUuid(ban.getJudge()).getUsername());

            if (expiration != -1)
                builder.append("\n&7Your ban will expire on &6" + TimeUtils.longToDate(expiration));

            builder.append("\n\n" + ban.getReason());

            event.setResult(ResultedEvent.ComponentResult.denied(builder.build(user)));
            user.remove();
        }

        // TODO: RealJoin. Will need injection.
    }

}
