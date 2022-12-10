/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

public final class GrammarUtils {

    public static String capitalization(String string) {
        if (string.length() > 1)
            return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        return string.toUpperCase();
    }

}
