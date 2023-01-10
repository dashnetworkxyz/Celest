/*
 * Copyright (C) 2023 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parsers;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.arguments.Parser;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;
import java.util.UUID;

public final class PlayerParser implements Parser {

    private static final ProxyServer server = Celest.getServer();

    @Override
    public Object parse(User user, String input) {
        if (input.matches("@[PpSs]") && user != null)
            return user.getPlayer();

        Optional<Player> optional;

        if (StringUtils.matchesUuid(input))
            optional = server.getPlayer(UUID.fromString(input));
        else
            optional = server.getPlayer(input);

        if (optional.isEmpty())
            return null;

        Player player = optional.get();
        User selected = User.getUser(player);

        if (user == null || user.canSee(selected))
            return player;

        return null;
    }

}
