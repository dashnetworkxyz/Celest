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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public final class ComponentUtils {

    private static final LegacyComponentSerializer legacy = LegacyComponentSerializer.legacySection();
    private static final GsonComponentSerializer gson = GsonComponentSerializer.gson();

    public static Component fromLegacyString(@NotNull String string) {
         return legacy.deserialize(ColorUtils.fromAmpersand(string));
    }

    public static String toLegacyString(@NotNull Component component) { return legacy.serialize(component); }

    public static Component fromJson(@NotNull String json) { return gson.deserialize(json); }

}
