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

package xyz.dashnetwork.celest.util;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public final class LazyUtil {

    @SafeVarargs
    public static <T> boolean anyTrue(@NotNull Predicate<T> predicate, @NotNull T... objects) {
        for (T each : objects)
            if (predicate.test(each))
                return true;
        return false;
    }

    public static boolean anyEquals(@NotNull Object object, @NotNull Object... compare) {
        return anyTrue(check -> check.equals(object), compare);
    }

    public static boolean anyEqualsIgnoreCase(@NotNull String string, @NotNull String... compare) {
        return anyTrue(check -> check.equalsIgnoreCase(string), compare);
    }

}
