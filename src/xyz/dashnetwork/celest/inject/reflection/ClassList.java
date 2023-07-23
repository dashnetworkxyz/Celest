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

package xyz.dashnetwork.celest.inject.reflection;

public final class ClassList {

    public static Class<?>
            VELOCITY_SERVER,
            MINECRAFT_CONNECTION, MINECRAFT_CONNECTION_ASSOCIATION, MINECRAFT_SESSION_HANDLER,
            HANDSHAKE_SESSION_HANDLER, INITIAL_INBOUND_CONNECTION, STATUS_SESSION_HANDLER,
            SERVER_LIST_PING_HANDLER, VELOCITY_INBOUND_CONNECTION,
            CONNECTION_MANAGER, SERVER_CHANNEL_INITIALIZER_HOLDER,
            STATE_REGISTRY,
            HANDSHAKE, STATUS_REQUEST, STATUS_RESPONSE;

    static {
        try {
            VELOCITY_SERVER = Class.forName("com.velocitypowered.proxy.VelocityServer");
            MINECRAFT_CONNECTION = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnection");
            MINECRAFT_CONNECTION_ASSOCIATION = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnectionAssociation");
            MINECRAFT_SESSION_HANDLER = Class.forName("com.velocitypowered.proxy.connection.MinecraftSessionHandler");
            HANDSHAKE_SESSION_HANDLER = Class.forName("com.velocitypowered.proxy.connection.client.HandshakeSessionHandler");
            INITIAL_INBOUND_CONNECTION = Class.forName("com.velocitypowered.proxy.connection.client.InitialInboundConnection");
            STATUS_SESSION_HANDLER = Class.forName("com.velocitypowered.proxy.connection.client.StatusSessionHandler");
            SERVER_LIST_PING_HANDLER = Class.forName("com.velocitypowered.proxy.connection.util.ServerListPingHandler");
            VELOCITY_INBOUND_CONNECTION = Class.forName("com.velocitypowered.proxy.connection.util.VelocityInboundConnection");
            CONNECTION_MANAGER = Class.forName("com.velocitypowered.proxy.network.ConnectionManager");
            SERVER_CHANNEL_INITIALIZER_HOLDER = Class.forName("com.velocitypowered.proxy.network.ServerChannelInitializerHolder");
            STATE_REGISTRY = Class.forName("com.velocitypowered.proxy.protocol.StateRegistry");
            HANDSHAKE = Class.forName("com.velocitypowered.proxy.protocol.packet.Handshake");
            STATUS_REQUEST = Class.forName("com.velocitypowered.proxy.protocol.packet.StatusRequest");
            STATUS_RESPONSE = Class.forName("com.velocitypowered.proxy.protocol.packet.StatusResponse");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

}
