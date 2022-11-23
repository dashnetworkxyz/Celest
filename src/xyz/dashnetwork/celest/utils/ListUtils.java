/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import java.util.List;

public final class ListUtils {

    public static boolean containsOtherThan(List<?> list, Object object) {
        return list.size() > (list.contains(object) ? 1 : 0);
    }

}
