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

public final class IntegerParser implements ArgumentParser {

    @Override
    public Object parse(User user, String input) {
        if (StringUtils.matchesInteger(input)) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ignored) {}
        }

        return null;
    }

}
