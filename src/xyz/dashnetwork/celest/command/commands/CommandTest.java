/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.Command;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;
import xyz.dashnetwork.celest.utils.profile.ProfileUtils;
import xyz.dashnetwork.celest.utils.storage.LegacyParser;

import java.util.Optional;
import java.util.UUID;

public final class CommandTest extends Command {

    public CommandTest() {
        super("test");

        setPermission(User::isOwner, true);
        addArguments(ArgumentType.MESSAGE);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<String> optional = arguments.get(String.class);

        if (optional.isEmpty()) {
            MessageUtils.message(source, "no u");
            return;
        }

        String[] args = optional.get().split(" ");

        if (args.length < 2) {
            MessageUtils.message(source, "no u");
            return;
        }

        if (args[0].equalsIgnoreCase("import-legacy")) {
            LegacyParser parser = new LegacyParser();

            MessageUtils.message(source, "reading...");
            parser.read();

            MessageUtils.message(source, "writing...");
            parser.write();
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
