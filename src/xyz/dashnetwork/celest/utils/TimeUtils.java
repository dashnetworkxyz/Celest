/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import java.text.SimpleDateFormat;

public class TimeUtils {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy, hh:mm a z");

    public static String longToDate(long time) { return formatter.format(time); }

    public static long fromTimeArgument(String string) {
        int length = string.length();

        if (length < 2)
            return -1;

        String subbed = string.substring(0, length - 1);

        if (!subbed.matches("^\\d+$"))
            return -1;

        char last = string.toLowerCase().charAt(length - 1);

        for (TimeType type : TimeType.values())
            if (type.getSelector() == last)
                return type.toLong() * Long.parseLong(subbed);

        return -1;
    }

}
