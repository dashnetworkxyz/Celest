/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public final class ListUtils {

    public static <T> String convertToString(@NotNull List<T> list, @NotNull Function<T, String> function, @NotNull String separator) {
        StringBuilder builder = new StringBuilder();

        for (T each : list) {
            if (builder.length() > 0)
                builder.append(separator);

            builder.append(function.apply(each));
        }

        return builder.toString();
    }

    public static <T> boolean equals(@NotNull List<T> list1, @NotNull List<T> list2) {
        int size = list1.size();

        if (size != list2.size())
            return false;

        for (int i = 0; i < size; i++)
            if (!list1.get(i).equals(list2.get(i)))
                return false;

        return true;
    }

    public static boolean containsOtherThan(@NotNull List<?> list, Object object) {
        return list.size() > (list.contains(object) ? 1 : 0);
    }

    public static void addIfStarts(@NotNull List<String> list, @NotNull String start, @NotNull String entry) {
        if (entry.toLowerCase().startsWith(start.toLowerCase()))
            list.add(entry);
    }

}
