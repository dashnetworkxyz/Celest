/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parser.parsers;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.arguments.parser.Parser;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;

public final class ServerParser implements Parser {

    @Override
    public Object parse(User user, String input) {
        Optional<RegisteredServer> optional = Celest.getServer().getServer(input);

        if (optional.isEmpty())
            return null;
        
        RegisteredServer server = optional.get();
        String name = server.getServerInfo().getName().toLowerCase();

        if (user == null || user.isOwner() ||  user.getPlayer().hasPermission("dashnetwork.server." + name))
            return server;

        return null;
    }

}
