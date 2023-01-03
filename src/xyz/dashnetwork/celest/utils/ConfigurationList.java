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

    private static String parse(String string) { return ColorUtils.fromAmpersand(Variables.parse(string)); }

    private static String[] parse(String[] array) {
        List<String> list = new ArrayList<>();

        for (String each : array)
            list.add(parse(each));

        return list.toArray(String[]::new);
    }

}
