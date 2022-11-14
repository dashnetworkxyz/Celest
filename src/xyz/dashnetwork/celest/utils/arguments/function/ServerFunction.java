/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.arguments.function;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.Celest;

import java.util.Optional;
import java.util.function.Function;

public final class ServerFunction implements Function<String, RegisteredServer> {

    private static final ProxyServer server = Celest.getServer();

    @Override
    public RegisteredServer apply(String string) {
        Optional<RegisteredServer> optional = server.getServer(string);

        return optional.orElse(null);
    }

}
