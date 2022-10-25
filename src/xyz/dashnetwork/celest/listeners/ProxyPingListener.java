/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;
import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.utils.Address;
import xyz.dashnetwork.celest.utils.ConfigurationList;
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.Variables;
import xyz.dashnetwork.celest.utils.chat.ColorUtils;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.data.AddressData;
import xyz.dashnetwork.celest.utils.data.PunishData;

import java.util.UUID;

public final class ProxyPingListener {

    @Subscribe
    public void onProxyPing(ProxyPingEvent event) {
        String address = event.getConnection().getRemoteAddress().getHostString();
        ServerPing.Builder builder = event.getPing().asBuilder();
        int online = 0;

        for (User user : User.getUsers())
            if (!user.getData().getVanish())
                online++;

        // TODO: Put Variables.parse() inside the ConfigurationList entry.
        Component description = ComponentUtils.toComponent(Variables.parse(ConfigurationList.MOTD_DESCRIPTION));
        String software = ColorUtils.fromAmpersand(Variables.parse(ConfigurationList.MOTD_SOFTWARE));

        builder.clearMods().clearSamplePlayers();
        builder.onlinePlayers(online);
        builder.description(description);
        builder.version(new ServerPing.Version(builder.getVersion().getProtocol(), software));

        for (String line : ConfigurationList.MOTD_HOVER)
            builder.samplePlayers(new ServerPing.SamplePlayer(ColorUtils.fromAmpersand(Variables.parse(line)), UUID.randomUUID()));

        event.setPing(builder.build());

        AddressData addressData = Address.getAddress(address).getData();
        PunishData ban = addressData.getBan();

        if (ban != null) {
            long expiration = ban.getExpiration();

            if (expiration == -1 || expiration > System.currentTimeMillis())
                return; // Don't do Pingspy for banned ips.
        }

        for (User user : User.getUsers())
            if (user.getData().getAddress().equals(address))
                return; // Some clients ping the server while connected (notably Lunar Client)

        // TODO: Pingspy
    }

}
