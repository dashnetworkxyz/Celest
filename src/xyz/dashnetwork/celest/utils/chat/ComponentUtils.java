/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public final class ComponentUtils {

    private static final LegacyComponentSerializer legacy = LegacyComponentSerializer.legacySection();

    public static TextComponent toComponent(@NotNull String string) {
        return legacy.deserialize(ColorUtils.fromAmpersand(string));
    }

    public static String fromComponent(@NotNull Component component) {
        return legacy.serialize(component);
    }

}
