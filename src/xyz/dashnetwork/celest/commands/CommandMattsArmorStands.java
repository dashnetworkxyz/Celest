/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.commands;

import com.velocitypowered.api.command.SimpleCommand;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;

public final class CommandMattsArmorStands implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        MessageUtils.message(invocation.source(), "&c&lMattsArmorStands &6&l>> &6Developed by MM5. Version &cv1.0");
    }

}
