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

package xyz.dashnetwork.celest.utils.chat;

import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

public final class ColorUtils {

    public static String fromAmpersand(@NotNull String string) {
        return string.replaceAll("&([0-f]|[k-o]|r|x)", "§$1");
    }

    public static String strip(@NotNull String string) {
        return string.replaceAll("[&§]([0-f]|[k-o]|r|x)", "");
    }

    public static String fromNamedTextColor(NamedTextColor named) {
        return switch (named.value()) {
            case 0 -> "§0";
            case 170 -> "§1";
            case 43520 -> "§2";
            case 43690 -> "§3";
            case 11141120 -> "§4";
            case 11141290 -> "§5";
            case 16755200 -> "§6";
            case 11184810 -> "§7";
            case 5592405 -> "§8";
            case 5592575 -> "§9";
            case 5635925 -> "§a";
            case 5636095 -> "§b";
            case 16733525 -> "§c";
            case 16733695 -> "§d";
            case 16777045 -> "§e";
            default -> "§f";
        };
    }

}
