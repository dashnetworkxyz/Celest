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
import com.velocitypowered.api.scheduler.Scheduler;
import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.*;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.Messages;
import xyz.dashnetwork.celest.utils.data.AddressData;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;
import xyz.dashnetwork.celest.utils.profile.ProfileUtils;

import java.util.UUID;

public final class ProxyPingListener {

    private static final Scheduler scheduler = Celest.getServer().getScheduler();

    @Subscribe
    public void onProxyPing(ProxyPingEvent event) {
        ServerPing.Builder builder = event.getPing().asBuilder();
        int online = 0;

        for (User user : User.getUsers())
            if (!user.getData().getVanish())
                online++;

        Component description = ComponentUtils.toComponent(ConfigurationList.MOTD_DESCRIPTION);
        String software = ConfigurationList.MOTD_SOFTWARE;

        builder.clearMods().clearSamplePlayers();
        builder.onlinePlayers(online);
        builder.description(description);
        builder.version(new ServerPing.Version(builder.getVersion().getProtocol(), software));

        for (String line : ConfigurationList.MOTD_HOVER)
            builder.samplePlayers(new ServerPing.SamplePlayer(line, UUID.randomUUID()));

        event.setPing(builder.build());

        InboundConnection connection = event.getConnection();
        final String hostname = connection.getRemoteAddress().getHostString();
        final ProtocolVersion protocolVersion = connection.getProtocolVersion();

        // Run async so Pingspy doesn't hold up the status response.
        scheduler.buildTask(Celest.getInstance(), () -> {
            Address address = Address.getAddress(hostname, true);
            AddressData data = address.getData();
            PlayerProfile[] profiles = data.getProfiles();

            if (profiles.length == 0)
                return; // Skip server pingers.

            if (address.isManual())
                return; // Skip players already logged in.

            if (PunishUtils.isValid(data.getBan()))
                return; // Skip banned ips.

            if (TimeUtils.isRecent(address.getServerPingTime(), TimeType.MINUTE))
                return; // Skip ping spammers.

            address.setServerPingTime(System.currentTimeMillis());

            // TODO: Detect and remove cloudflare proxy from input address.

            String name = hostname;
            String inputAddress = address.getInputServerAddress();
            String inputPort = String.valueOf(address.getInputServerPort());
            String version = VersionUtils.getVersionString(protocolVersion);
            String protocol = String.valueOf(protocolVersion.getProtocol());
            String usernames = ArrayUtils.convertToString(profiles, PlayerProfile::getUsername, "&6, ");

            PlayerProfile recent = ProfileUtils.findMostRecent(profiles);

            if (recent != null)
                name = recent.getUsername();

            MessageUtils.broadcast(user -> user.getData().getPingSpy(), Messages.playerPingSpy(
                    name, hostname, inputAddress, inputPort, version, protocol, usernames
            ));
        }).schedule();
    }

}
