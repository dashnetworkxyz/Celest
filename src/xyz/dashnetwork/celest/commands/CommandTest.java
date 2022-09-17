/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import xyz.dashnetwork.celest.storage.Cache;
import xyz.dashnetwork.celest.storage.Storage;
import xyz.dashnetwork.celest.utils.*;

import java.util.UUID;

public class CommandTest implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();

        if (source.hasPermission("dashnetwork.owner")) {
            String[] args = invocation.arguments();

            if (args.length < 2) {
                MessageUtils.message(source, "no u");
                return;
            }

            if (args[0].equalsIgnoreCase("username")) {
                PlayerProfile profile = MojangUtils.fromUsername(args[1]);

                MessageUtils.message(source, "name: " + profile.getUsername() + " uuid: " + profile.getUuid());
            }

            if (args[0].equalsIgnoreCase("uuid")) {
                PlayerProfile profile = MojangUtils.fromUuid(UUID.fromString(args[1]));

                MessageUtils.message(source, "name: " + profile.getUsername() + " uuid: " + profile.getUuid());
            }
        }
    }

}
