/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

public class LazyUtils {

    public static boolean anyEqualIgnoreCase(String string, String... compare) {
        for (String each : compare)
            if (each.equalsIgnoreCase(string))
                return true;
        return false;
    }

}
