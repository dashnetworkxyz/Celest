/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection;

public final class ClassList {

    public static final Class<?>
            VELOCITY_SERVER,
            MINECRAFT_CONNECTION, MINECRAFT_CONNECTION_ASSOCIATION, MINECRAFT_SESSION_HANDLER,
            VELOCITY_INBOUND_CONNECTION,
            HANDSHAKE_SESSION_HANDLER, INITIAL_INBOUND_CONNECTION, STATUS_SESSION_HANDLER,
            CONNECTION_MANAGER, SERVER_CHANNEL_INITIALIZER_HOLDER,
            STATE_REGISTRY,
            HANDSHAKE, STATUS_REQUEST;

    static {
        try {
            VELOCITY_SERVER = Class.forName("com.velocitypowered.proxy.VelocityServer");
            MINECRAFT_CONNECTION = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnection");
            MINECRAFT_CONNECTION_ASSOCIATION = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnectionAssociation");
            MINECRAFT_SESSION_HANDLER = Class.forName("com.velocitypowered.proxy.connection.MinecraftSessionHandler");
            VELOCITY_INBOUND_CONNECTION = Class.forName("com.velocitypowered.proxy.connection.util.VelocityInboundConnection");
            HANDSHAKE_SESSION_HANDLER = Class.forName("com.velocitypowered.proxy.connection.client.HandshakeSessionHandler");
            INITIAL_INBOUND_CONNECTION = Class.forName("com.velocitypowered.proxy.connection.client.InitialInboundConnection");
            STATUS_SESSION_HANDLER = Class.forName("com.velocitypowered.proxy.connection.client.StatusSessionHandler");
            CONNECTION_MANAGER = Class.forName("com.velocitypowered.proxy.network.ConnectionManager");
            SERVER_CHANNEL_INITIALIZER_HOLDER = Class.forName("com.velocitypowered.proxy.network.ServerChannelInitializerHolder");
            STATE_REGISTRY = Class.forName("com.velocitypowered.proxy.protocol.StateRegistry");
            HANDSHAKE = Class.forName("com.velocitypowered.proxy.protocol.packet.Handshake");
            STATUS_REQUEST = Class.forName("com.velocitypowered.proxy.protocol.packet.StatusRequest");
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

}
