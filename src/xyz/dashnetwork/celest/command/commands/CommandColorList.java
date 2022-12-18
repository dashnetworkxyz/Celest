/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;

public class CommandColorList extends CelestCommand {

    private static final LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();

    public CommandColorList() { super("colorlist", "colors"); }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        String message = "§6§l»§1 &1 §2&2 §3&3 §4&4 §5&5 §6&6 §7&7 §8&8 §9&9 §0&0 §a&a §b&b §c&c §d&d §e&e §f&f"
                + "\n§6§l»§f §k&k§f(&k) §l&l§f §m&m§f §n&n§f §o&o";

        MessageUtils.message(source, serializer.deserialize(message));
    }

}
