/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.Command;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;

public final class CommandMattsArmorStands extends Command {

    public CommandMattsArmorStands() { super("mattsarmorstands"); }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        MessageUtils.message(source, "&c&lMattsArmorStands &6&l>> &6Developed by MM5. Version &cv1.0");
    }

}
