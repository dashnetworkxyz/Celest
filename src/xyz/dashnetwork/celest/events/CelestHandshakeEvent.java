/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.events;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.InboundConnection;

public final class CelestHandshakeEvent {

    private final InboundConnection connection;
    private final ProtocolVersion version;
    private final String address;
    private final int port;
    private final int next;

    public CelestHandshakeEvent(InboundConnection connection, ProtocolVersion version, String address, int port, int next) {
        this.connection = connection;
        this.version = version;
        this.address = address;
        this.port = port;
        this.next = next;
    }

    public InboundConnection getInboundConnection() { return connection; }

    public ProtocolVersion getProtocolVersion() { return version; }

    public String getServerAddress() { return address; }

    public int getPort() { return port; }

    public int getNextState() { return next; }

}
