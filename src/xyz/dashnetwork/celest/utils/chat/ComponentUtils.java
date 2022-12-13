/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
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
