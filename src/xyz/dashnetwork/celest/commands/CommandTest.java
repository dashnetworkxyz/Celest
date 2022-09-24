/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import xyz.dashnetwork.celest.User;
import xyz.dashnetwork.celest.utils.MessageUtils;
import xyz.dashnetwork.celest.utils.PlayerProfile;
import xyz.dashnetwork.celest.utils.PredicateUtils;
import xyz.dashnetwork.celest.utils.ProfileUtils;

import java.util.UUID;

public class CommandTest implements SimpleCommand {

    @Override
    public boolean hasPermission(Invocation invocation) {
        return PredicateUtils.permission(User::isOwner, true, invocation.source());
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (args.length < 2) {
            MessageUtils.message(source, "no u");
            return;
        }

        if (args[0].equalsIgnoreCase("username")) {
            PlayerProfile profile = ProfileUtils.fromUsername(args[1]);

            if (profile == null)
                profile = new PlayerProfile(UUID.randomUUID(), "null");

            MessageUtils.message(source, "name: " + profile.getUsername() + " uuid: " + profile.getUuid());
        }

        if (args[0].equalsIgnoreCase("uuid")) {
            PlayerProfile profile = ProfileUtils.fromUuid(UUID.fromString(args[1]));

            if (profile == null)
                profile = new PlayerProfile(UUID.randomUUID(), "null");

            MessageUtils.message(source, "name: " + profile.getUsername() + " uuid: " + profile.getUuid());
        }
    }

}
