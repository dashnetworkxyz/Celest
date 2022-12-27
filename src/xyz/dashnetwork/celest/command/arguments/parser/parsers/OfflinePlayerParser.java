/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parser.parsers;

import xyz.dashnetwork.celest.command.arguments.parser.ArgumentParser;
import xyz.dashnetwork.celest.utils.OfflineUser;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;
import xyz.dashnetwork.celest.utils.profile.ProfileUtils;

import java.util.UUID;

public class OfflinePlayerParser implements ArgumentParser {

    @Override
    public Object parse(User user, String input) {
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
