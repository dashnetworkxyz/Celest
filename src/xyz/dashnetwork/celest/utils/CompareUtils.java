/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

public final class CompareUtils {

    public static boolean equalsWithNull(Object object1, Object object2) {
        if (object1 == null || object2 == null)
            return object1 == null && object2 == null;

        return object1.equals(object2);
    }

}
