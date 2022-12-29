/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parser.parsers;

import xyz.dashnetwork.celest.command.arguments.parser.ArgumentParser;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.UUID;

public final class UniqueIdParser implements ArgumentParser {

    @Override
    public Object parse(User user, String input) {
        if (StringUtils.matchesUuid(input))
            return UUID.fromString(input);

        return null;
    }

}
