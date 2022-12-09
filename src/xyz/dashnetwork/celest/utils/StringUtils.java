/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import org.jetbrains.annotations.NotNull;

public final class StringUtils {

    // negative index can be used as a reverse index.
    public static String unsplit(int index, @NotNull String spliterator, @NotNull String[] array) {
        StringBuilder builder = new StringBuilder();
        int length = array.length;

        if (index < 0) {
            length += index;
            index = 0;
        }

        for (int i = index; i < length; i++) {
            if (i > index)
                builder.append(spliterator);

            builder.append(array[i]);
        }

        return builder.toString();
    }

    public static boolean matchesUuid(@NotNull String string) {
        return string.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
    }

    public static boolean matchesInteger(@NotNull String string) {
        return string.matches("\\b(?<!\\.)\\d+(?!\\.)\\b");
    }

}
