/*
 * Copyright (C) 2023 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.suggesters;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.arguments.Suggester;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class ServerSuggester implements Suggester {

    @Override
    public List<String> suggest(User user, String input) {
        List<String> list = new ArrayList<>();

        for (RegisteredServer server : Celest.getServer().getAllServers()) {
            String name = server.getServerInfo().getName();

            if (user.isOwner() || user.getPlayer().hasPermission("dashnetwork.server." + name))
                ListUtils.addIfStarts(list, input, name);
        }

        return list;
    }

}
