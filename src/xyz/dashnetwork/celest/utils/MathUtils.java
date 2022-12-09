/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

public final class MathUtils {

    public static int getLowest(int... integers) {
        int selected = integers[0];

        for (int each : integers)
            if (each < selected)
                selected = each;

        return selected;
    }

}
