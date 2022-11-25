/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parser;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.FunctionPair;

import java.util.ArrayList;
import java.util.List;

public final class PlayerListParser implements FunctionPair<CommandSource, String, List<Player>> {

    private static final ProxyServer server = Celest.getServer();
    private static final PlayerParser function = new PlayerParser();

    @Override
    public List<Player> apply(CommandSource source, String string) {
        List<Player> list = new ArrayList<>();

        if (string.equalsIgnoreCase("@a"))
            list.addAll(server.getAllPlayers());
        else {
            for (String each : string.split(",")) {
                Player player = function.apply(source, each);

                if (player != null)
                    list.add(player);
            }
        }

        if (list.isEmpty())
            return null;

        return list;
    }

}
