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

import java.util.UUID;

public final class LoginListener {

    @Subscribe
    public void onLogin(LoginEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        PunishData ban = user.getBan();

        if (!PunishUtils.isValid(ban))
            ban = user.getAddress().getData().getBan();

        if (PunishUtils.isValid(ban)) {
            Long expiration = ban.getExpiration();
            UUID uuid = ban.getJudge();

            String type = expiration == null ? "permanently" : "temporarily";
            String judge = uuid == null ? "Console" : ProfileUtils.fromUuid(uuid).getUsername();

            MessageBuilder builder = new MessageBuilder();
            builder.append("&6&lDashNetwork");
            builder.append("\n&7You have been " + type + " banned");
            builder.append("\n&7You were banned by &6" + judge);

            if (expiration != null)
                builder.append("\n&7Your ban will expire on &6" + TimeUtils.longToDate(expiration));

            builder.append("\n\n" + ban.getReason());

            event.setResult(ResultedEvent.ComponentResult.denied(builder.build(user)));
            user.remove();
        }

        // TODO: RealJoin. Will need injection.
    }

}
