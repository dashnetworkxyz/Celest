/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.Celest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SelectorUtils {

    private static final ProxyServer server = Celest.getServer();

    public static Collection<Player> getPlayers(Player source, @NotNull String selection) {
        String[] args = selection.split(",");
        Set<Player> players = new HashSet<>(); // Use HashSet so we don't get duplicates.

        for (String arg : args) {
            if (source != null && LazyUtils.anyEqualsIgnoreCase(arg, "@p", "@s"))
                players.add(source);
            else if (arg.equalsIgnoreCase("@a"))
                players.addAll(server.getAllPlayers());
            else
                server.getPlayer(arg).ifPresent(players::add);
        }

        return players;
    }

}
