/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
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

    public static boolean matchesUrl(@NotNull String string) {
        return string.matches("(?:(https?)://)?([-\\w_.]{2,}\\.[a-z]{2,4})(/\\S*)?");
    }

    public static boolean matchesIp(@NotNull String string) {
        return string.matches("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
    }

    public static boolean matchesCloudflare(@NotNull String string) {
        return string.matches("_dc-srv\\.([a-f0-9]{12})\\._minecraft\\._tcp\\.(?:([A-z0-9]+)\\.)?(?i)dashnetwork\\.xyz");
    }

    public static boolean matchesUsername(@NotNull String string) {
        return string.matches("([0-z]|_){1,16}");
    }

}
