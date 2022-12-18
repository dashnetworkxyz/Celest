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
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.chat.builder.formats.AddressFormat;
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
        final InboundConnection connection = event.getConnection();
        final ProtocolVersion version = connection.getProtocolVersion();
        ServerPing.Builder builder = event.getPing().asBuilder();
        int online = 0;
        int protocol;

        if (VersionUtils.isLegacy(version))
            protocol = 48;
        else
            protocol = version.getProtocol();

        for (User user : User.getUsers())
            if (!user.getData().getVanish())
                online++;

        Component description = ComponentUtils.fromLegacyString(ConfigurationList.MOTD_DESCRIPTION);
        String software = ConfigurationList.MOTD_SOFTWARE;

        builder.clearMods().clearSamplePlayers();
        builder.onlinePlayers(online);
        builder.maximumPlayers(Calendar.getInstance().get(Calendar.YEAR));
        builder.description(description);
        builder.version(new ServerPing.Version(protocol, software));

        for (String line : ConfigurationList.MOTD_HOVER)
            builder.samplePlayers(new ServerPing.SamplePlayer(line, UUID.randomUUID()));

        event.setPing(builder.build());

        // Run async so PingSpy doesn't hold up the status response.
        scheduler.buildTask(Celest.getInstance(), () -> {
            Address address = Address.getAddress(connection.getRemoteAddress().getHostString(), true);
            AddressData data = address.getData();
            PlayerProfile[] profiles = data.getProfiles();

            if (profiles.length == 0)
                return; // Skip server pingers.

            if (PunishUtils.isValid(data.getBan()))
                return; // Skip banned ips.

            if (TimeUtils.isRecent(address.getServerPingTime(), TimeType.MINUTE))
                return; // Skip ping spammers.

            address.setServerPingTime(System.currentTimeMillis());

            Optional<InetSocketAddress> optional = connection.getVirtualHost();
            InetSocketAddress virtual;

            if (optional.isPresent()) {
                InetSocketAddress socket = optional.get();
                String clean = socket.getHostString();

                // Filter Cloudflare proxy.
                if (clean.matches("_dc-srv\\.([a-f0-9]{12})\\._minecraft\\._tcp\\.([A-z0-9]+)\\.(?i)dashnetwork\\.xyz"))
                    clean = clean.replaceFirst("_dc-srv\\.([A-z0-9]{12})\\._minecraft\\._tcp\\.", "");

                virtual = new InetSocketAddress(clean, socket.getPort());
            } else
                virtual = new InetSocketAddress("Unknown", -1);

            String range = VersionUtils.getVersionString(version);
            String name = profiles[0].getUsername();
            String usernames = ArrayUtils.convertToString(profiles, PlayerProfile::getUsername, ", ");
            PlayerProfile recent = ProfileUtils.findMostRecent(profiles);

            if (recent != null)
                name = recent.getUsername();

            MessageBuilder message = new MessageBuilder();
            TextSection section = message.append("&6&l»&6 " + name + "&7 pinged the server.");

            section.hover(new AddressFormat(address));
            section.hover("&7\nVirtual: &6" + virtual.getHostName() + ":" + virtual.getPort());
            section.hover("&7\nVersion: &6" + range + "&7 (" + version.getProtocol() + ")");
            section.hover("\nAccounts: &6" + usernames);

            MessageUtils.broadcast(user -> user.getData().getPingSpy(), message::build);
        }).schedule();
    }

}
