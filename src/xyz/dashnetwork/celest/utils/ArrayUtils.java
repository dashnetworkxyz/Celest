/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class ArrayUtils {

    public static <T>String convertToString(@NotNull Function<T, String> function, @NotNull T[] objects) {
        StringBuilder builder = new StringBuilder();

        for (T each : objects) {
            if (builder.length() > 0)
                builder.append(", ");

            builder.append(function.apply(each));
        }

        return builder.toString();
    }

    public static <T>T[] add(@NotNull T[] array, T object) {
        List<T> list = new ArrayList<>(List.of(array));
        list.add(object);

        return list.toArray(array);
    }

    public static <T>T[] addAll(@NotNull T[] array, @NotNull Collection<T> objects) {
        List<T> list = new ArrayList<>(List.of(array));
        list.addAll(objects);

        return list.toArray(array);
    }

    public static <T>T[] remove(@NotNull IntFunction<T[]> function, @NotNull T[] array, T object) {
        List<T> list = new ArrayList<>(List.of(array));
        list.remove(object);

        return list.toArray(function);
    }

    public static <T>T[] remove(@NotNull IntFunction<T[]> function, @NotNull T[] array, int position) {
        List<T> list = new ArrayList<>(List.of(array));
        list.remove(position);

        return list.toArray(function);
    }

    public static <T>T[] removeAll(@NotNull IntFunction<T[]> function, @NotNull T[] array, @NotNull Collection<T> objects) {
        List<T> list = new ArrayList<>(List.of(array));
        list.removeAll(objects);

        return list.toArray(function);
    }

}
