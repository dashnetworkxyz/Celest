/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import org.jetbrains.annotations.NotNull;

public final class LazyUtils {

    public static boolean anyEquals(@NotNull Object object, Object... compare) {
        for (Object each : compare)
            if (each.equals(object))
                return true;
        return false;
    }

    public static boolean anyEqualsIgnoreCase(@NotNull String string, @NotNull String... compare) {
        for (String each : compare)
            if (each.equalsIgnoreCase(string))
                return true;
        return false;
    }

}
