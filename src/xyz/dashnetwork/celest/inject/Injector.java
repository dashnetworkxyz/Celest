/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
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

    // Hook into Handshake and Server Ping.
    // Allows me to see client specified address and port from handshake.
    // Allows custom ServerPing response for custom entry preventsChatReports:true (CelestServerPing)
    public static void injectChannelInitializer(ProxyServer proxy) {
        try {
            ReflectedVelocityServer velocity = new ReflectedVelocityServer(proxy);
            ReflectedConnectionManager connectionManager = velocity.getConnectionManager();
            ReflectedServerChannelInitializerHolder holder = connectionManager.getServerChannelInitializer();

            holder.set(new CelestServerChannelInitializer(holder.get()));
        } catch (ReflectiveOperationException | RuntimeException exception) {
            Celest.getLogger().error("Failed to inject server channel initializer. Printing stacktrace...");
            exception.printStackTrace();
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
        } catch (ReflectiveOperationException | RuntimeException exception) {
            Celest.getLogger().error("Failed to inject play session handler. Printing stacktrace...");
            exception.printStackTrace();
        }
    }

}
