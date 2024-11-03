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
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.InboundConnection;
import com.velocitypowered.api.proxy.server.ServerPing;
import com.velocitypowered.api.scheduler.Scheduler;
import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.*;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.Section;
import xyz.dashnetwork.celest.utils.connection.Address;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;
import xyz.dashnetwork.celest.utils.storage.Cache;
import xyz.dashnetwork.celest.utils.storage.data.AddressData;
import xyz.dashnetwork.celest.utils.storage.data.CacheData;

import java.net.InetSocketAddress;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

public final class ProxyPingListener {

    private static final Scheduler scheduler = Celest.getServer().getScheduler();

    @Subscribe
    public void onProxyPing(ProxyPingEvent event) {
        final InboundConnection connection = event.getConnection();
        Optional<InetSocketAddress> virtual = connection.getVirtualHost();

        if (virtual.isEmpty() || !virtual.get().getHostName().toLowerCase().contains("dashnetwork")) {
            event.setResult(ResultedEvent.GenericResult.denied());
            return;
        }

        final ProtocolVersion version = connection.getProtocolVersion();
        ServerPing.Builder builder = event.getPing().asBuilder();
        int online = 0;

        for (User user : User.getUsers())
            if (!user.getData().getVanish())
                online++;

        Component description = ComponentUtils.fromString(ConfigurationList.MOTD_DESCRIPTION);
        String software = ConfigurationList.MOTD_SOFTWARE;

        builder.clearMods().clearSamplePlayers();
        builder.onlinePlayers(online);
        builder.maximumPlayers(Calendar.getInstance().get(Calendar.YEAR));
        builder.description(description);
        builder.version(new ServerPing.Version(version.getProtocol(), software));

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

            for (User user : User.getUsers())
                if (user.getAddress().equals(address))
                    return; // Skip players already online.

            address.setServerPingTime(System.currentTimeMillis());

            String range = VersionUtils.getVersionString(version);
            String name = profiles[0].username();

            if (profiles.length > 1) {
                CacheData recent = Cache.findMostRecent(profiles);

                if (recent != null)
                    name = recent.getUsername();
            }

            MessageBuilder message = new MessageBuilder();
            Section section = message.append("&6&l»&6 " + name + "&7 pinged the server.");

            section.hover("&6" + address.getString(), User::showAddress);
            section.hover("&7Version: &6" + range + "&7 (" + version.getProtocol() + ")");
            section.hover("&7Accounts: &6" + ArrayUtils.convertToString(profiles, PlayerProfile::username, "&7, &6"));

            message.broadcast(user -> user.getData().getPingSpy());
        }).schedule();
    }

}
