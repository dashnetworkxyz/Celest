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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class ArrayUtils {

    public static <T> String convertToString(@NotNull T[] array, @NotNull Function<T, String> function, @NotNull String separator) {
        StringBuilder builder = new StringBuilder();

        for (T each : array) {
            if (!builder.isEmpty())
                builder.append(separator);

            builder.append(function.apply(each));
        }

        return builder.toString();
    }

    public static <T> T[] add(@NotNull T[] array, T object) {
        List<T> list = new ArrayList<>(List.of(array));
        list.add(object);

        return list.toArray(array);
    }

}
