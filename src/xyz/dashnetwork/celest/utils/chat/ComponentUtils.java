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

package xyz.dashnetwork.celest.utils.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class ComponentUtils {

    private static final LegacyComponentSerializer legacy = LegacyComponentSerializer.legacySection();
    private static final GsonComponentSerializer gson = GsonComponentSerializer.gson();

    public static Component fromString(@NotNull String string) {
        return legacy.deserialize(ColorUtils.fromAmpersand(string));
    }

    public static String toString(@NotNull Component component) { return legacy.serialize(component); }

    public static Component fromJson(@NotNull String json) { return gson.deserialize(json); }

    public static List<Component> getAllChildren(Component component) {
        return new ArrayList<>(findChildren(component));
    }

    private static List<Component> findChildren(Component component) {
        List<Component> children = new ArrayList<>();

        for (Component child : component.children()) {
            children.add(child);

            if (!child.children().isEmpty())
                children.addAll(findChildren(child));
        }

        return children;
    }

}
