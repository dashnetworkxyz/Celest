/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parser;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.utils.FunctionPair;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.TimeUtils;

public final class LongParser implements FunctionPair<CommandSource, String, Long> {

    @Override
    public Long apply(CommandSource source, String string) {
        long parsed = TimeUtils.fromTimeArgument(string);

        if (parsed != -1)
            return parsed;

        if (StringUtils.matchesInteger(string)) {
            try {
                return Long.parseLong(string);
            } catch (NumberFormatException ignored) {}
        }

        return null;
    }

}
