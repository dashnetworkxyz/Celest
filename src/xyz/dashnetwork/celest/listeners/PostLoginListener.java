/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.ArrayList;
import java.util.List;

public final class PostLoginListener {

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        Player player = event.getPlayer();
        User user = User.getUser(player);
        UserData data = user.getData();
        MessageBuilder builder = new MessageBuilder();
        Format format = new NamedSourceFormat(user);

        if (data.getVanish()) {
            builder.append("&3&l»&r ");
            builder.append(format);
            builder.append("&3 silently joined.");

            MessageUtils.broadcast(each -> each.isStaff() || each.getData().getVanish(), builder::build);
        } else if (data.getLastPlayed() == null) {
            builder.append("&6&l»&6 Welcome, ");
            builder.append(format);
            builder.append("&6, to &lDashNetwork");

            MessageUtils.broadcast(builder::build);
        } else {
            builder.append("&a&l»&r ");
            builder.append(format);
            builder.append("&a joined.");

            MessageUtils.broadcast(builder::build);
        }

        List<PlayerProfile> list = new ArrayList<>(List.of(user.getAddress().getData().getProfiles()));
        list.removeIf(each -> each.getUuid().equals(player.getUniqueId()));

        if (!list.isEmpty()) {
            builder = new MessageBuilder();
            builder.append("&6&l»&r ");
            builder.append("&7Hover for &6" + list.size() + "&7 alts of")
                    .hover("&7Alts for " + player.getUsername() + ":\n&6"
                            + ListUtils.convertToString(list, PlayerProfile::getUsername, "\n"));
            builder.append("&r ");
            builder.append(format);

            MessageUtils.broadcast(each -> each.getData().getAltSpy(), builder::build);
        }

        user.getAddress().setServerPingTime(-1); // Reset for PingSpy
    }

}
