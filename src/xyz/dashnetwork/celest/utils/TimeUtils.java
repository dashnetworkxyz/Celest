/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

public final class TimeUtils {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy, hh:mm a z");

    public static String longToDate(long time) { return formatter.format(time); }

    public static boolean isRecent(long time, long compare) { return compare > System.currentTimeMillis() - time; }

    public static boolean isRecent(long time, TimeType compare) { return isRecent(time, compare.toMillis()); }

    public static long fromTimeArgument(@NotNull String string) {
        if (!string.matches("(\\d{1,18})([A-z]+)"))
            return -1;

        String first = string.replaceAll("[A-z]", "");
        String last = string.replaceAll("\\d", "");

        for (TimeType type : TimeType.values())
            for (String selector : type.getSelectors())
                if (selector.equalsIgnoreCase(last))
                    return type.toMillis(Long.parseLong(first));

        return -1;
    }

}
