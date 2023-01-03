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
import org.jetbrains.annotations.NotNull;

public enum Variables {

    VERSION_HIGHEST(ProtocolVersion.MAXIMUM_VERSION.getMostRecentSupportedVersion()),
    VERSION_LOWEST(ProtocolVersion.MINIMUM_VERSION.getVersionIntroducedIn());

    private final String replace;

    Variables(String replace) { this.replace = replace; }

    public static String parse(@NotNull String input) {
        for (Variables variable : values())
            input = input.replace("{$" + variable.name() + "}", variable.replace);

        return input;
    }

}
