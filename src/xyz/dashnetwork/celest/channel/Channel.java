/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.channel;

import com.velocitypowered.api.proxy.ProxyServer;
import xyz.dashnetwork.celest.Celest;

public abstract class Channel {

    private static final ProxyServer server = Celest.getServer();

    public Channel() {
        server.getChannelRegistrar().register();
    }

}
