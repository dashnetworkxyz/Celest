/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import xyz.dashnetwork.celest.events.CelestHandshakeEvent;

public final class CelestHandshakeListener {

    @Subscribe
    public void onCelestHandshake(CelestHandshakeEvent event) {
        System.out.println("--- handshake received ---");
        System.out.println("version: " + event.getProtocolVersion().name());
        System.out.println("address: " + event.getServerAddress());
        System.out.println("port: " + event.getPort());
        System.out.println("next: " + event.getNextState());
    }

}
