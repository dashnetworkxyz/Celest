/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import xyz.dashnetwork.celest.utils.chat.ColorUtils;
import xyz.dashnetwork.celest.utils.storage.Configuration;

import java.util.ArrayList;
import java.util.List;

public final class ConfigurationList {

    public static String MOTD_DESCRIPTION, MOTD_SOFTWARE;
    public static String[] MOTD_HOVER;

    public static void load() {
        MOTD_DESCRIPTION = parse(Configuration.get(String.class, "motd.description"));
        MOTD_SOFTWARE = parse(Configuration.get(String.class, "motd.software"));
        MOTD_HOVER = parse(Configuration.get(String[]::new, "motd.hover"));
    }

    private static String parse(String string) {
        return ColorUtils.fromAmpersand(Variables.parse(string));
    }

    private static String[] parse(String[] array) {
        List<String> list = new ArrayList<>();

        for (String each : array)
            list.add(parse(each));

        return list.toArray(String[]::new);
    }

}
