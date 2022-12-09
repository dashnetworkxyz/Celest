/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parser;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.CastUtils;
import xyz.dashnetwork.celest.utils.FunctionPair;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;

public final class ServerParser implements FunctionPair<CommandSource, String, RegisteredServer> {

    private static final ProxyServer proxy = Celest.getServer();

    @Override
    public RegisteredServer apply(CommandSource source, String string) {
        Optional<RegisteredServer> optional = proxy.getServer(string);

        if (optional.isEmpty())
            return null;
        
        RegisteredServer server = optional.get();
        String name = server.getServerInfo().getName().toLowerCase();
        User user = CastUtils.toUser(source);

        if (source.hasPermission("dashnetwork.server." + name) || (user != null && user.isOwner()))
            return server;

        return null;
    }

}
