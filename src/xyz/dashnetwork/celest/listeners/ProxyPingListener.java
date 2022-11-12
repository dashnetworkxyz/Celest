/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.InboundConnection;
import com.velocitypowered.api.proxy.server.ServerPing;
import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.*;
import xyz.dashnetwork.celest.utils.chat.ColorUtils;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.data.AddressData;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;

import java.util.UUID;

public final class ProxyPingListener {

    @Subscribe
    public void onProxyPing(ProxyPingEvent event) {
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

        // TODO: Pingspy

        InboundConnection connection = event.getConnection();
        final String hostname = connection.getRemoteAddress().getHostString();
        final ProtocolVersion version = connection.getProtocolVersion();

        // Run async so Pingspy doesn't hold up the status response.
        Celest.getServer().getScheduler().buildTask(Celest.getInstance(), () -> {
            Address address = Address.getAddress(hostname);
            AddressData data = address.getData();
            PlayerProfile[] profiles = data.getProfiles();

            if (profiles.length == 0)
                return; // Skip server pingers.

            if (address.isManual())
                return; // Skip players already logged in.

            if (PunishUtils.isValid(data.getBan()))
                return; // Skip banned ips.


        }).schedule();
    }

}
