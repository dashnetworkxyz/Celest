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
import xyz.dashnetwork.celest.utils.StringUtils;

import java.util.Optional;
import java.util.UUID;

public final class PlayerParser implements FunctionPair<CommandSource, String, Player> {

    private static final ProxyServer server = Celest.getServer();

    @Override
    public Player apply(CommandSource source, String string) {
        if (string.matches("@[ps]") && source instanceof Player)
            return (Player) source;

        Optional<Player> optional;

        if (StringUtils.matchesUuid(string))
            optional = server.getPlayer(UUID.fromString(string));
        else
            optional = server.getPlayer(string);

        return optional.orElse(null);
    }

}
