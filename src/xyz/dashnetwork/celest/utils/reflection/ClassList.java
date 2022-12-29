/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection;

public final class ClassList {

    public static Class<?>
            VELOCITY_SERVER,
            MINECRAFT_CONNECTION, MINECRAFT_CONNECTION_ASSOCIATION, MINECRAFT_SESSION_HANDLER,
            BACKEND_PLAY_SESSION_HANDLER, VELOCITY_SERVER_CONNECTION,
            CLIENT_PLAY_SESSION_HANDLER, CONNECTED_PLAYER, HANDSHAKE_SESSION_HANDLER, INITIAL_INBOUND_CONNECTION, STATUS_SESSION_HANDLER,
            SERVER_LIST_PING_HANDLER, VELOCITY_INBOUND_CONNECTION,
            CONNECTION_MANAGER, SERVER_CHANNEL_INITIALIZER_HOLDER,
            STATE_REGISTRY,
            SESSION_PLAYER_CHAT, SESSION_PLAYER_COMMAND,
            HANDSHAKE, STATUS_REQUEST, STATUS_RESPONSE;

    static {
        try {
            VELOCITY_SERVER = Class.forName("com.velocitypowered.proxy.VelocityServer");
            MINECRAFT_CONNECTION = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnection");
            MINECRAFT_CONNECTION_ASSOCIATION = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnectionAssociation");
            MINECRAFT_SESSION_HANDLER = Class.forName("com.velocitypowered.proxy.connection.MinecraftSessionHandler");
            BACKEND_PLAY_SESSION_HANDLER = Class.forName("com.velocitypowered.proxy.connection.backend.BackendPlaySessionHandler");
            VELOCITY_SERVER_CONNECTION = Class.forName("com.velocitypowered.proxy.connection.backend.VelocityServerConnection");
            CLIENT_PLAY_SESSION_HANDLER = Class.forName("com.velocitypowered.proxy.connection.client.ClientPlaySessionHandler");
            CONNECTED_PLAYER = Class.forName("com.velocitypowered.proxy.connection.client.ConnectedPlayer");
            HANDSHAKE_SESSION_HANDLER = Class.forName("com.velocitypowered.proxy.connection.client.HandshakeSessionHandler");
            INITIAL_INBOUND_CONNECTION = Class.forName("com.velocitypowered.proxy.connection.client.InitialInboundConnection");
            STATUS_SESSION_HANDLER = Class.forName("com.velocitypowered.proxy.connection.client.StatusSessionHandler");
            SERVER_LIST_PING_HANDLER = Class.forName("com.velocitypowered.proxy.connection.util.ServerListPingHandler");
            VELOCITY_INBOUND_CONNECTION = Class.forName("com.velocitypowered.proxy.connection.util.VelocityInboundConnection");
            CONNECTION_MANAGER = Class.forName("com.velocitypowered.proxy.network.ConnectionManager");
            SERVER_CHANNEL_INITIALIZER_HOLDER = Class.forName("com.velocitypowered.proxy.network.ServerChannelInitializerHolder");
            STATE_REGISTRY = Class.forName("com.velocitypowered.proxy.protocol.StateRegistry");
            SESSION_PLAYER_CHAT = Class.forName("com.velocitypowered.proxy.protocol.packet.chat.session.SessionPlayerChat");
            SESSION_PLAYER_COMMAND = Class.forName("com.velocitypowered.proxy.protocol.packet.chat.session.SessionPlayerCommand");
            HANDSHAKE = Class.forName("com.velocitypowered.proxy.protocol.packet.Handshake");
            STATUS_REQUEST = Class.forName("com.velocitypowered.proxy.protocol.packet.StatusRequest");
            STATUS_RESPONSE = Class.forName("com.velocitypowered.proxy.protocol.packet.StatusResponse");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

}
