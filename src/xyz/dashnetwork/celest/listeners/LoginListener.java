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
import xyz.dashnetwork.celest.utils.*;

public class LoginListener {

    @Subscribe
    public void onLogin(LoginEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        UserData userData = user.getData();
        AddressData addressData = user.getAddress().getData();
        PunishData userBan = userData.getBan();
        PunishData ipBan = addressData.getBan();

        if (userBan != null || ipBan != null) {
            PunishData selectedBan = userBan;

            if (selectedBan == null)
                selectedBan = ipBan;

            long expiration = selectedBan.getExpiration();

            if (expiration == -1 || expiration > System.currentTimeMillis()) {
                String reason = selectedBan.getReason();
                String banner = ProfileUtils.fromUuid(selectedBan.getBanner()).getUsername();
                String date = TimeUtils.longToDate(expiration);

                Component message = expiration == -1 ?
                        Messages.loginBanned(reason, banner) :
                        Messages.loginBannedTemporary(reason, banner, date);

                event.setResult(ResultedEvent.ComponentResult.denied(message));

                user.remove();
            }
        }
    }

}
