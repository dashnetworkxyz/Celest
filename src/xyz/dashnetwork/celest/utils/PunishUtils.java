/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import xyz.dashnetwork.celest.utils.data.PunishData;

public final class PunishUtils {

    public static boolean isValid(PunishData data) {
        if (data == null)
            return false;

        long expiration = data.getExpiration();

        return expiration == -1 || expiration > System.currentTimeMillis();
    }

}
