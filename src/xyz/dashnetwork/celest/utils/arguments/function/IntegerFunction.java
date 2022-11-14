/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.arguments.function;

import xyz.dashnetwork.celest.utils.StringUtils;

import java.util.function.Function;

public final class IntegerFunction implements Function<String, Integer> {

    @Override
    public Integer apply(String string) {
        if (StringUtils.matchesInteger(string)) {
            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException ignored) {}
        }

        return null;
    }

}
