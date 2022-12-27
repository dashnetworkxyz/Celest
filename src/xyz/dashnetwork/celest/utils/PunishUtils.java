/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import xyz.dashnetwork.celest.utils.storage.data.PunishData;

public final class PunishUtils {

    public static boolean isValid(PunishData data) {
        if (data == null)
            return false;

        Long expiration = data.getExpiration();

        return expiration == null || expiration > System.currentTimeMillis();
    }

}
