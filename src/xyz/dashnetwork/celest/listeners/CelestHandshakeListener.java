/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import xyz.dashnetwork.celest.events.CelestHandshakeEvent;
import xyz.dashnetwork.celest.utils.Address;

public final class CelestHandshakeListener {

    @Subscribe
    public void onCelestHandshake(CelestHandshakeEvent event) {
        String hostname = event.getInboundConnection().getRemoteAddress().getHostString();
        Address address = Address.getAddress(hostname);

        address.setInputServerAddress(event.getServerAddress());
        address.setInputServerPort(event.getPort());
    }

}
