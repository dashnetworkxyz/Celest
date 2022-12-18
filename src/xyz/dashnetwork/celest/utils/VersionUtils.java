/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.util.ProxyVersion;
import xyz.dashnetwork.celest.Celest;

public final class VersionUtils {

    private static final ProxyVersion version = Celest.getServer().getVersion();

    public static boolean isLegacy(ProtocolVersion version) {
        return LazyUtils.anyEquals(version, ProtocolVersion.MINECRAFT_1_7_2, ProtocolVersion.MINECRAFT_1_7_6);
    }

    public static String getVersionString(ProtocolVersion version) {
        String earliest = version.getVersionIntroducedIn();
        String latest = version.getMostRecentSupportedVersion();

        if (earliest.equals(latest))
            return latest;

        return earliest + "-" + latest;
    }

    public static String getProxyName() { return version.getName(); }

    public static String getProxyVersion() { return version.getVersion(); }

}
