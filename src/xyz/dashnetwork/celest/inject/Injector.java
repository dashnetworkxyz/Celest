/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.inject;

import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.inject.reflection.velocity.ReflectedVelocityServer;
import xyz.dashnetwork.celest.inject.reflection.velocity.network.ReflectedConnectionManager;
import xyz.dashnetwork.celest.inject.reflection.velocity.network.ReflectedServerChannelInitializerHolder;
import xyz.dashnetwork.celest.inject.server.CelestServerChannelInitializer;

public final class Injector {

    private static final Logger logger = Celest.getLogger();

    // Hook into Server Ping.
    // Allows custom ServerPing response for custom entry preventsChatReports:true (CelestServerPing)
    public static void injectChannelInitializer(ProxyServer proxy) {
        try {
            ReflectedVelocityServer velocity = new ReflectedVelocityServer(proxy);
            ReflectedConnectionManager connectionManager = velocity.getConnectionManager();
            ReflectedServerChannelInitializerHolder holder = connectionManager.getServerChannelInitializer();

            holder.set(new CelestServerChannelInitializer(holder.get()));
        } catch (ReflectiveOperationException exception) {
            logger.error("Failed to inject server channel initializer.", exception);
        }
    }

}
