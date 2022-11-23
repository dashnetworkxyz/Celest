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
import xyz.dashnetwork.celest.utils.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public final class PlayerFunction implements Function<String, Player> {

    private static final ProxyServer server = Celest.getServer();

    @Override
    public Player apply(String string) {
        if (string == null) // Useful for PlayerListFunction
            return null;

        Optional<Player> optional;

        if (StringUtils.matchesUuid(string))
            optional = server.getPlayer(UUID.fromString(string));
        else
            optional = server.getPlayer(string);

        return optional.orElse(null);
    }

}
