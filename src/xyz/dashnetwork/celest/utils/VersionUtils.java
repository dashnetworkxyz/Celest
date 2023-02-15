/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.network.ProtocolVersion;

public final class VersionUtils {

    public static boolean isLegacy(ProtocolVersion version) {
        return LazyUtils.anyEquals(version, ProtocolVersion.MINECRAFT_1_7_2, ProtocolVersion.MINECRAFT_1_7_6);
    }

    public static String getVersionString(ProtocolVersion version) {
        String earliest = version.getVersionIntroducedIn();
        String latest = version.getMostRecentSupportedVersion();

        if (earliest.equals(latest))
            return earliest;

        return earliest + "-" + latest;
    }

}