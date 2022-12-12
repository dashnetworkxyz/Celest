/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parser.parsers;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.arguments.parser.ArgumentParser;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;
import java.util.UUID;

public final class PlayerParser implements ArgumentParser {

    private static final ProxyServer server = Celest.getServer();

    @Override
    public Object parse(User user, String string) {
        if (string.matches("@[PpSs]") && user != null)
            return user.getPlayer();

        Optional<Player> optional;

        if (StringUtils.matchesUuid(string))
            optional = server.getPlayer(UUID.fromString(string));
        else
            optional = server.getPlayer(string);

        if (optional.isEmpty())
            return null;

        Player player = optional.get();
        User selected = User.getUser(player);

        if (user == null || user.canSee(selected))
            return player;

        return null;
    }

}
