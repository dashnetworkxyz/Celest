/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.network.ProtocolVersion;

public class VariableUtils {

    private enum Variable {

        VERSION_HIGHEST(ProtocolVersion.MAXIMUM_VERSION.getMostRecentSupportedVersion()),
        VERSION_LOWEST(ProtocolVersion.MINIMUM_VERSION.getVersionIntroducedIn());

        private final String replace;

        Variable(String replace) {
            this.replace = replace;
        }

        public String getReplace() { return replace; }

    }

    public static String parse(String input) {
        for (Variable variable : Variable.values())
            input = input.replace("{" + variable.name() + "}", variable.getReplace());

        return input;
    }

}
