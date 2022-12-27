/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.storage.LegacyParser;

import java.util.Optional;

public final class CommandTest extends CelestCommand {

    public CommandTest() {
        super("test");

        setPermission(User::isOwner, true);
        addArguments(ArgumentType.STRING);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<String> optional = arguments.get(String.class);

        if (optional.isPresent()) {
            String string = optional.get();

            if (string.equalsIgnoreCase("legacy-import")) {
                MessageUtils.message(source, "reading legacy data...");

                LegacyParser parser = new LegacyParser();
                parser.read();

                MessageUtils.message(source, "writing legacy data...");

                parser.write();

                MessageUtils.message(source, "legacy import complete.");
                return;
            }
        }

        MessageUtils.message(source, "no u");

        // Component component = builder.build(null);
        // String json = GsonComponentSerializer.gson().serialize(component);
    }

}
