/*
 * Copyright (c) 2022. Andrew Bell.
 * All rights reserved.
 */

package xyz.dashnetwork.celest.commands;

import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;

public class CommandTest implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        invocation.source().sendMessage(Component.text("test"));
    }

}
