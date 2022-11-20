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

    @SafeVarargs // This could do unsafe things, so be careful when using this.
    public static <T>boolean anyTrue(Predicate<T> predicate, T... objects) {
        for (T each : objects)
            if (predicate.test(each))
                return true;
        return false;
    }

    // Deprecate?
    public static boolean anyEquals(@NotNull Object object, Object... compare) {
        return anyTrue(check -> check.equals(object), compare);
    }

    // Deprecate?
    public static boolean anyEqualsIgnoreCase(@NotNull String string, @NotNull String... compare) {
        return anyTrue(check -> check.equalsIgnoreCase(string), compare);
    }

}
