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

public final class IntegerParser implements FunctionPair<CommandSource, String, Integer> {

    @Override
    public Integer apply(CommandSource source, String string) {
        if (StringUtils.matchesInteger(string)) {
            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException ignored) {}
        }

        return null;
    }

}
