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
import xyz.dashnetwork.celest.utils.connection.Address;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;
import xyz.dashnetwork.celest.utils.profile.ProfileUtils;
import xyz.dashnetwork.celest.utils.storage.data.AddressData;

import java.net.InetSocketAddress;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

public final class ProxyPingListener {

    private static final Scheduler scheduler = Celest.getServer().getScheduler();

    @Subscribe
    public void onProxyPing(ProxyPingEvent event) {
        ServerPing.Builder builder = event.getPing().asBuilder();
        final int protocol = builder.getVersion().getProtocol();
        int online = 0;

        for (User user : User.getUsers())
            if (!user.getData().getVanish())
                online++;


        Component description = ComponentUtils.toComponent(ConfigurationList.MOTD_DESCRIPTION);
        String software = ConfigurationList.MOTD_SOFTWARE;

        builder.clearMods().clearSamplePlayers();
        builder.onlinePlayers(online);
        builder.maximumPlayers(Calendar.getInstance().get(Calendar.YEAR));
        builder.description(description);
        builder.version(new ServerPing.Version(protocol, software));

        for (String line : ConfigurationList.MOTD_HOVER)
            builder.samplePlayers(new ServerPing.SamplePlayer(line, UUID.randomUUID()));

        event.setPing(builder.build());

        final InboundConnection connection = event.getConnection();

        // Run async so PingSpy doesn't hold up the status response.
        scheduler.buildTask(Celest.getInstance(), () -> {
            String hoststring = connection.getRemoteAddress().getHostString();
            ProtocolVersion protocolVersion = connection.getProtocolVersion();
            Optional<InetSocketAddress> virtual = connection.getVirtualHost();
            String inputAddress = "N/A";
            String inputPort = "N/A";

            if (virtual.isPresent()) {
                InetSocketAddress socket = virtual.get();
                inputAddress = socket.getHostString();
                inputPort = String.valueOf(socket.getPort());
            }

            Address address = Address.getAddress(hoststring, true);
            AddressData data = address.getData();
            PlayerProfile[] profiles = data.getProfiles();

            if (profiles.length == 0)
                return; // Skip server pingers.

            if (PunishUtils.isValid(data.getBan()))
                return; // Skip banned ips.

            if (TimeUtils.isRecent(address.getServerPingTime(), TimeType.MINUTE))
                return; // Skip ping spammers.

            address.setServerPingTime(System.currentTimeMillis());

            String name = profiles[0].getUsername();
            String version = VersionUtils.getVersionString(protocolVersion);
            String stringProtocol = String.valueOf(protocol);
            String usernames = ArrayUtils.convertToString(profiles, PlayerProfile::getUsername, ", ");
            PlayerProfile recent = ProfileUtils.findMostRecent(profiles);

            if (recent != null)
                name = recent.getUsername();

            // TODO: Hide Address on sensitiveData setting
            MessageUtils.broadcast(user -> user.getData().getPingSpy(), Messages.playerPingSpy(
                    name, hoststring, inputAddress, inputPort, version, stringProtocol, usernames
            ));
        }).schedule();
    }

}
