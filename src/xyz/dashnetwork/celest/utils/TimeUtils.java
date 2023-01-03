/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public final class TimeUtils {

    private static final SimpleDateFormat formatter;

    static {
        formatter = new SimpleDateFormat("MMM d, yyyy, hh:mm a z");
        formatter.setTimeZone(TimeZone.getTimeZone("MST"));
    }

    public static String longToDate(long time) { return formatter.format(time); }

    public static boolean isRecent(long time, TimeType compare) { return isRecent(time, compare.toMillis()); }

    public static boolean isRecent(long time, long compare) { return compare > System.currentTimeMillis() - time; }

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
