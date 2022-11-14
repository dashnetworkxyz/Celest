/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.arguments.function;

import xyz.dashnetwork.celest.utils.StringUtils;

import java.util.UUID;
import java.util.function.Function;

public final class UniqueIdFunction implements Function<String, UUID> {

    @Override
    public UUID apply(String string) {
        if (StringUtils.matchesUuid(string))
            return UUID.fromString(string);

        return null;
    }

}
