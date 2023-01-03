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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class ArrayUtils {

    public static <T> String convertToString(@NotNull T[] array, @NotNull Function<T, String> function, @NotNull String separator) {
        StringBuilder builder = new StringBuilder();

        for (T each : array) {
            if (builder.length() > 0)
                builder.append(separator);

            builder.append(function.apply(each));
        }

        return builder.toString();
    }

    public static <T> boolean containsOtherThan(@NotNull T[] array, T other) {
        return array.length > (contains(array, other) ? 1 : 0);
    }

    public static <T> boolean contains(@NotNull T[] array, T check) {
        for (T each : array)
            if (each.equals(check))
                return true;
        return false;
    }

    public static <T> T[] add(@NotNull T[] array, T object) {
        List<T> list = new ArrayList<>(List.of(array));
        list.add(object);

        return list.toArray(array);
    }

    public static <T> T[] addAll(@NotNull T[] array, @NotNull Collection<T> objects) {
        List<T> list = new ArrayList<>(List.of(array));
        list.addAll(objects);

        return list.toArray(array);
    }

    public static <T> T[] remove(@NotNull IntFunction<T[]> function, @NotNull T[] array, T object) {
        List<T> list = new ArrayList<>(List.of(array));
        list.remove(object);

        return list.toArray(function);
    }

    public static <T> T[] remove(@NotNull IntFunction<T[]> function, @NotNull T[] array, int position) {
        List<T> list = new ArrayList<>(List.of(array));
        list.remove(position);

        return list.toArray(function);
    }

    public static <T> T[] removeAll(@NotNull IntFunction<T[]> function, @NotNull T[] array, @NotNull Collection<T> objects) {
        List<T> list = new ArrayList<>(List.of(array));
        list.removeAll(objects);

        return list.toArray(function);
    }

}
