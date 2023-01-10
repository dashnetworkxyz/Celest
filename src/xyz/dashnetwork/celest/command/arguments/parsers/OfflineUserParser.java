/*
 * Copyright (C) 2023 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parsers;

import xyz.dashnetwork.celest.command.arguments.Parser;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.connection.OfflineUser;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;
import xyz.dashnetwork.celest.utils.profile.ProfileUtils;

import java.util.UUID;

public final class OfflineUserParser implements Parser {

    @Override
    public Object parse(User user, String input) {
        if (input.matches("@[PpSs]") && user != null)
            return user;

        PlayerProfile profile;

        if (StringUtils.matchesUuid(input))
            profile = ProfileUtils.fromUuid(UUID.fromString(input));
        else
            profile = ProfileUtils.fromUsername(input);

        if (profile == null)
            return null;

        return OfflineUser.getOfflineUser(profile);
    }

}
