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

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ListUtils {

    @Deprecated
    public static <T> String convertToString(@NotNull List<T> list, @NotNull Function<T, String> function, @NotNull String separator) {
        return list.stream().map(function).collect(Collectors.joining(separator));
    }

    @Deprecated
    public static <T> boolean equals(@NotNull List<T> list1, @NotNull List<T> list2) {
        return list1.equals(list2);
    }

    public static boolean containsOtherThan(@NotNull List<?> list, Object object) {
        return list.size() > (list.contains(object) ? 1 : 0);
    }

    public static void addIfStarts(@NotNull List<String> list, @NotNull String start, @NotNull String entry) {
        if (entry.toLowerCase().startsWith(start.toLowerCase()))
            list.add(entry);
    }

}
