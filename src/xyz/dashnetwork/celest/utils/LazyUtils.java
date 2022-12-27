/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public final class LazyUtils {

    @SafeVarargs
    public static <T> boolean anyTrue(@NotNull Predicate<T> predicate, @NotNull T... objects) {
        for (T each : objects)
            if (predicate.test(each))
                return true;
        return false;
    }

    @Deprecated
    public static boolean anyEquals(@NotNull Object object, @NotNull Object... compare) {
        return anyTrue(check -> check.equals(object), compare);
    }

    @Deprecated
    public static boolean anyEqualsIgnoreCase(@NotNull String string, @NotNull String... compare) {
        return anyTrue(check -> check.equalsIgnoreCase(string), compare);
    }

}
