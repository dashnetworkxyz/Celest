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

package xyz.dashnetwork.celest.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.util.LazyUtil;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.formats.CommandSourceFormat;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.sql.data.UserData;

public final class DisconnectListener {

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);

        if (LazyUtil.anyEquals(event.getLoginStatus(),
                DisconnectEvent.LoginStatus.PRE_SERVER_JOIN,
                DisconnectEvent.LoginStatus.SUCCESSFUL_LOGIN)) {
            UserData data = user.getData();
            MessageBuilder builder = new MessageBuilder();

            if (data.getVanish()) {
                builder.append("&3&l»&6 ");
                builder.append(new CommandSourceFormat(user));
                builder.append("&3 silently left.");
                builder.broadcast(each -> each.isStaff() || each.getData().getVanish());
            } else {
                builder.append("&c&l»&6 ");
                builder.append(new CommandSourceFormat(user));
                builder.append("&c left.");
                builder.broadcast();
            }
        }

        user.remove();
    }

}
