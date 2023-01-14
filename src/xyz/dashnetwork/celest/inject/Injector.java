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

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.inject.backend.handler.CelestClientPlaySessionHandler;
import xyz.dashnetwork.celest.inject.server.CelestServerChannelInitializer;
import xyz.dashnetwork.celest.utils.reflection.velocity.ReflectedVelocityServer;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.backend.ReflectedBackendPlaySessionHandler;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.backend.ReflectedVelocityServerConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedClientPlaySessionHandler;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedConnectedPlayer;
import xyz.dashnetwork.celest.utils.reflection.velocity.network.ReflectedConnectionManager;
import xyz.dashnetwork.celest.utils.reflection.velocity.network.ReflectedServerChannelInitializerHolder;

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

    // Hacky workaround for issue #804. (https://github.com/PaperMC/Velocity/issues/804)
    // This completely disables chat signatures.
    public static void injectSessionHandler(Player player) {
        try {
            ReflectedConnectedPlayer connected = new ReflectedConnectedPlayer(player);
            ReflectedVelocityServerConnection server = connected.getConnectedServer();
            ReflectedMinecraftConnection serverConnection = server.ensureConnected();
            ReflectedBackendPlaySessionHandler backend = new ReflectedBackendPlaySessionHandler(serverConnection.getSessionHandler());
            ReflectedMinecraftConnection playerConnection = backend.playerConnection();
            ReflectedClientPlaySessionHandler client = new ReflectedClientPlaySessionHandler(playerConnection.getSessionHandler());

            playerConnection.eventLoop().execute(() -> {
                try {
                    playerConnection.setSessionHandler(new CelestClientPlaySessionHandler(client));
                } catch (ReflectiveOperationException exception) {
                    exception.printStackTrace();
                }
            });
            connected.setPlayerKey(null);
        } catch (ReflectiveOperationException exception) {
            logger.error("Failed to inject play session handler.", exception);
        }
    }

}
