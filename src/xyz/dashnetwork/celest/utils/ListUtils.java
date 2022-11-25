/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ListUtils {

    public static boolean containsOtherThan(@NotNull List<?> list, Object object) {
        return list.size() > (list.contains(object) ? 1 : 0);
    }

    public static void addIfStarts(@NotNull List<String> list, @NotNull String start, @NotNull String entry) {
        if (entry.toLowerCase().startsWith(start.toLowerCase()))
            list.add(entry);
    }

}
