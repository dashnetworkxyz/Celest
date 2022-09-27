/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

public class ArrayUtils {

    public static <T>T[] add(@NotNull T[] array, @NotNull T object) {
        List<T> list = new ArrayList<>(List.of(array));
        list.add(object);

        return list.toArray(array);
    }

    public static <T>T[] removeAll(@NotNull IntFunction<T[]> function, @NotNull T[] array, @NotNull List<T> objects) {
        List<T> list = new ArrayList<>(List.of(array));
        list.removeAll(objects);

        return list.toArray(function);
    }

}
