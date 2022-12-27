/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat;

import org.jetbrains.annotations.NotNull;

public final class ColorUtils {

    public static String fromAmpersand(@NotNull String string) {
        return string.replaceAll("&([0-f]|[k-o]|r|x)", "§$1");
    }

    public static String strip(@NotNull String string) {
        return string.replaceAll("[&§]([0-f]|[k-o]|r|x)", "");
    }

}
