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
import xyz.dashnetwork.celest.utils.AddressData;
import xyz.dashnetwork.celest.utils.Messages;
import xyz.dashnetwork.celest.utils.PunishData;
import xyz.dashnetwork.celest.utils.UserData;

public class LoginListener {

    @Subscribe
    public void onLogin(LoginEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        UserData userData = user.getData();
        AddressData addressData = user.getAddress().getAddressData();
        PunishData userBan = userData.getBan();
        PunishData ipBan = addressData.getBan();

        if (userBan != null || ipBan != null) {
            PunishData selectedBan = userBan;

            if (selectedBan == null)
                selectedBan = ipBan;

            long expiration = selectedBan.getExpiration();

            if (expiration == -1 || expiration > System.currentTimeMillis()) {
                String reason = selectedBan.getReason();
                Component message = expiration == -1 ?
                        Messages.loginBanned(reason, "") : // TODO: Username pulling
                        Messages.loginBannedTemporary(reason, "", ""); // TODO: TimeUtils

                event.setResult(ResultedEvent.ComponentResult.denied(message));

                user.remove(); // TODO: Check if this is needed.
            }
        }
    }

}
