/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import xyz.dashnetwork.celest.utils.storage.Configuration;

public final class ConfigurationList {

    public static String MOTD_DESCRIPTION, MOTD_SOFTWARE;
    public static String[] MOTD_HOVER;

    public static void load() {
        MOTD_DESCRIPTION = Configuration.get(String.class, "motd.description");
        MOTD_SOFTWARE = Configuration.get(String.class, "motd.software");
        MOTD_HOVER = Configuration.getArray(String[]::new, "motd.hover");
    }

}
