/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.LazyUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

public final class DisconnectListener {

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        if (LazyUtils.anyEquals(event.getLoginStatus(),
                DisconnectEvent.LoginStatus.PRE_SERVER_JOIN,
                DisconnectEvent.LoginStatus.SUCCESSFUL_LOGIN)) {
            Player player = event.getPlayer();
            User user = User.getUser(player);
            UserData data = user.getData();
            MessageBuilder builder = new MessageBuilder();

            if (data.getVanish()) {
                builder.append("&3&l»&r ");
                builder.append(new NamedSourceFormat(user));
                builder.append("&3 silently left.");

                MessageUtils.broadcast(each -> each.isStaff() || each.getData().getVanish(), builder::build);
            } else {
                builder.append("&c&l»&r ");
                builder.append(new NamedSourceFormat(user));
                builder.append("&c left.");

                MessageUtils.broadcast(builder::build);

                data.setLastPlayed(System.currentTimeMillis());
            }

            user.remove();
        }
    }

}
