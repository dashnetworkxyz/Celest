/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.arguments.function;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import xyz.dashnetwork.celest.Celest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class PlayerListFunction implements Function<String, List<Player>> {

    private static final ProxyServer server = Celest.getServer();
    private static final PlayerFunction function = new PlayerFunction();

    @Override
    public List<Player> apply(String string) {
        List<Player> list = new ArrayList<>();

        if (string.equalsIgnoreCase("@a"))
            list.addAll(server.getAllPlayers());
        else {
            for (String each : string.split(",")) {
                Player player = function.apply(each);

                if (player != null)
                    list.add(player);
            }
        }

        if (list.isEmpty())
            return null;

        return list;
    }

}
