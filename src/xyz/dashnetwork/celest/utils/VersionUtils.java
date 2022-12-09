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
        String earliest = version.getVersionIntroducedIn();
        String latest = version.getMostRecentSupportedVersion();

        if (earliest.equals(latest))
            return latest;

        return earliest + "-" + latest;
    }

}
