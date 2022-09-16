/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

public class ArrayUtils {

    // TODO: Use for AddressData UUID[] modifications?

    public static <T>T[] add(T[] array, T... objects) {
        List<T> list = new ArrayList<>(List.of(array));
        list.addAll(List.of(objects));

        return list.toArray(array);
    }

    public static <T>T[] remove(IntFunction<T[]> function, T[] array, T... objects) {
        List<T> list = new ArrayList<>(List.of(array));
        list.removeAll(List.of(objects));

        return list.toArray(function);
    }

}
