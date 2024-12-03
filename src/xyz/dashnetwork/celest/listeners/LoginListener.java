/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
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
            Long expiration = ban.expiration();
            UUID uuid = ban.judge();

            String type = expiration == null ? "permanently" : "temporarily";
            String judge = uuid == null ? "Console" : ProfileUtils.fromUuid(uuid).getName();

            MessageBuilder builder = new MessageBuilder();
            builder.append("&6&lDashNetwork");
            builder.append("\n&7You have been " + type + " banned");
            builder.append("\n&7You were banned by &6" + judge);

            if (expiration != null)
                builder.append("\n&7Your ban will expire on &6" + TimeUtils.longToDate(expiration));

            builder.append("\n\n" + ban.reason());

            event.setResult(ResultedEvent.ComponentResult.denied(builder.build(user)));
        }
    }

}
