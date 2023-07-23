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
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.InboundConnection;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;

import java.net.InetSocketAddress;
import java.util.Optional;

public final class PreLoginListener {

    @Subscribe
    public void onPreLogin(PreLoginEvent event) {
        InboundConnection connection = event.getConnection();
        ProtocolVersion version = connection.getProtocolVersion();
        Optional<InetSocketAddress> virtual = connection.getVirtualHost();

        if (virtual.isEmpty() || !virtual.get().getHostName().toLowerCase().contains("dashnetwork")) {
            event.setResult(PreLoginEvent.PreLoginComponentResult.denied(ComponentUtils.fromString(
                    "Connection Refused"
            )));
        }

        if (version.compareTo(ProtocolVersion.MINECRAFT_1_7_6) <= 0) {
            event.setResult(PreLoginEvent.PreLoginComponentResult.denied(ComponentUtils.fromString(
                    """
                            &6&lDashNetwork
                            &61.7&7 is no longer supported.
                            Please update to &61.8 or newer."""
            )));
        }

        // Some usernames break the rules. I'm not accounting for any of these.
        if (!StringUtils.matchesUsername(event.getUsername())) {
            event.setResult(PreLoginEvent.PreLoginComponentResult.denied(ComponentUtils.fromString(
                    """
                            &6&lDashNetwork
                            &7Illegal usernames are not supported."""
            )));
        }
    }

}
