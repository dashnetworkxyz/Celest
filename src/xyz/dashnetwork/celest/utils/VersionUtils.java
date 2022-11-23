/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.network.ProtocolVersion;

import java.util.List;

public final class VersionUtils {

    public static String getVersionString(ProtocolVersion version) {
        StringBuilder builder = new StringBuilder();
        List<String> list = version.getVersionsSupportedBy();
        int size = list.size();

        builder.append(list.get(0));

        if (size > 1)
            builder.append("-").append(list.get(size - 1));

        return builder.toString();
    }

}
