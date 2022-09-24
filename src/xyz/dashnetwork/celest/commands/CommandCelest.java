/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.commands;

import com.velocitypowered.api.command.SimpleCommand;
import xyz.dashnetwork.celest.User;
import xyz.dashnetwork.celest.utils.PredicateUtils;

public class CommandCelest implements SimpleCommand {

    @Override
    public boolean hasPermission(Invocation invocation) {
        return PredicateUtils.permission(User::isOwner, true, invocation.source());
    }

    @Override
    public void execute(Invocation invocation) {
        
    }

}
