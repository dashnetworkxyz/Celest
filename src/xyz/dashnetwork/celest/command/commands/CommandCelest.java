/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.ConfigurationList;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.limbo.Limbo;
import xyz.dashnetwork.celest.utils.storage.Configuration;
import xyz.dashnetwork.celest.utils.storage.LegacyParser;

import java.util.Optional;

public final class CommandCelest extends CelestCommand {

    public CommandCelest() {
        super("celest");

        setPermission(User::isOwner, true);
        addArguments(ArgumentType.STRING);
        setCompletions(0, "reload", "save", "clear-limbo");
    }

    private void sendHelpMessage(CommandSource source) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&6 Celest debug commands");
        builder.append("\n&6&l»&7 /celest reload").hover("&6Reload config.yml");
        builder.append("\n&6&l»&7 /celest save").hover("&6Force an auto-save");
        builder.append("\n&6&l»&7 /celest legacy-import &c(unsafe)").hover("&6Import legacy data &c(unsafe)");
        builder.append("\n&6&l»&7 /celest clear-limbo").hover("&6Force clear & save all objects in Limbo.");

        MessageUtils.message(source, builder::build);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<String> optionalCommand = arguments.get(String.class);

        if (optionalCommand.isEmpty()) {
            sendHelpMessage(source);
            return;
        }

        switch (optionalCommand.get().toLowerCase()) {
            case "reload":
                Configuration.load();
                ConfigurationList.load();

                MessageUtils.message(source, "&6&l»&7 config.yml reloaded.");
                break;
            case "save":
                Celest.getSaveTask().run();
                MessageUtils.message(source, "&6&l»&7 Save complete.");
                break;
            case "legacy-import":
                MessageUtils.message(source, "&6&l»&7 Reading legacy data...");

                LegacyParser parser = new LegacyParser();
                parser.read();

                MessageUtils.message(source, "&6&l»&7 Writing legacy data...");

                parser.write();

                MessageUtils.message(source, "&6&l»&7 Legacy import complete.");
                break;
            case "clear-limbo":
                for (Limbo<?> limbo : Limbo.getLimbos()) {
                    limbo.cancel();
                    limbo.save();
                }

                MessageUtils.message(source, "&6&l»&7 All objects in limbo cleared & saved.");
                break;
            default:
                sendHelpMessage(source);
        }
    }

}
