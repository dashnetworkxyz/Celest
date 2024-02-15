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

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class PostLoginListener {

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        UserData data = user.getData();
        MessageBuilder builder = new MessageBuilder();

        if (data.getVanish()) {
            builder.append("&3&l»&f ");
            builder.append(new NamedSourceFormat(user));
            builder.append("&3 silently joined.");
            builder.broadcast(each -> each.isStaff() || each.getData().getVanish());
        } else if (data.getLastPlayed() == null) {
            builder.append("&6&l»&6 Welcome, ");
            builder.append(new NamedSourceFormat(user));
            builder.append("&6, to &lDashNetwork");
            builder.broadcast();
        } else {
            builder.append("&a&l»&f ");
            builder.append(new NamedSourceFormat(user));
            builder.append("&a joined.");
            builder.broadcast();
        }

        List<PlayerProfile> list = new ArrayList<>(List.of(user.getAddress().getData().getProfiles()));
        list.removeIf(each -> each.uuid().equals(player.getUniqueId()));

        if (!list.isEmpty()) {
            builder = new MessageBuilder();
            builder.append("&6&l»&f ");
            builder.append("&7Hover for &6" + list.size() + "&7 alts of")
                    .hover("&7Alts for " + player.getUsername() + ":\n&6"
                            + list.stream().map(PlayerProfile::username).collect(Collectors.joining("\n")));
            builder.append("&f ");
            builder.append(new NamedSourceFormat(user));
            builder.broadcast(each -> each.getData().getAltSpy());
        }

        if (!user.isAuthenticated())
            MessageUtils.message(player, "&6&l»&7 Please enter your 2fa code into chat.");

        user.getAddress().setServerPingTime(-1); // Reset for PingSpy
    }

}
