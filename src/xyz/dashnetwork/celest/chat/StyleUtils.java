/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */


package xyz.dashnetwork.celest.chat;

import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;

public final class StyleUtils {

    public static boolean hasColor(Style style) {
        if (style.color() != null)
            return true;

        if (style.hasDecoration(TextDecoration.OBFUSCATED))
            return true;

        if (style.hasDecoration(TextDecoration.BOLD))
            return true;

        if (style.hasDecoration(TextDecoration.STRIKETHROUGH))
            return true;

        if (style.hasDecoration(TextDecoration.UNDERLINED))
            return true;

        return style.hasDecoration(TextDecoration.ITALIC);
    }

}
