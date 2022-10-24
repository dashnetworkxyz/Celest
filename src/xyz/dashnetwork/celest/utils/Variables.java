/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.network.ProtocolVersion;
import org.jetbrains.annotations.NotNull;

public enum Variables {

    VERSION_HIGHEST(ProtocolVersion.MAXIMUM_VERSION.getMostRecentSupportedVersion()),
    VERSION_LOWEST(ProtocolVersion.MINIMUM_VERSION.getVersionIntroducedIn());

    private final String replace;

    Variables(String replace) { this.replace = replace; }

    public String getReplace() { return replace; }

    public static String parse(@NotNull String input) {
        for (Variables variable : values())
            input = input.replace("{$" + variable.name() + "}", variable.getReplace());

        return input;
    }

}
