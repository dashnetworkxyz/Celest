/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parser;

import xyz.dashnetwork.celest.utils.FunctionPair;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.UUID;

public final class UniqueIdParser implements FunctionPair<User, String, UUID> {

    @Override
    public UUID apply(User user, String string) {
        if (StringUtils.matchesUuid(string))
            return UUID.fromString(string);

        return null;
    }

}
