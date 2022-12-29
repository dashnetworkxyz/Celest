/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.google.gson.Gson;
import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.ConfigurationList;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.connection.Address;
import xyz.dashnetwork.celest.utils.connection.OfflineUser;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.limbo.Limbo;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;
import xyz.dashnetwork.celest.utils.profile.ProfileUtils;
import xyz.dashnetwork.celest.utils.storage.Configuration;
import xyz.dashnetwork.celest.utils.storage.LegacyParser;
import xyz.dashnetwork.celest.utils.storage.Storage;
import xyz.dashnetwork.celest.utils.storage.data.CacheData;

import java.util.Optional;

public final class CommandCelest extends CelestCommand {

    private static final Gson gson = Celest.getGson();

    public CommandCelest() {
        super("celest");

        setPermission(User::isOwner, true);
        addArguments(ArgumentType.STRING);
        addArguments(ArgumentType.STRING, ArgumentType.STRING, ArgumentType.STRING);
        setCompletions(0, "reload", "save", "limbo", "cache", "user", "address");
    }

    private void sendHelpMessage(CommandSource source) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&6 Celest debug commands");
        builder.append("\n&6&l»&7 /celest reload").hover("&6Reload config.yml");
        builder.append("\n&6&l»&7 /celest save").hover("&6Force an auto-save");
        builder.append("\n&6&l»&7 /celest legacy-import &c(unsafe)").hover("&6Import legacy data &c(unsafe)");
        builder.append("\n&6&l»&7 /celest limbo").hover("&6Print objects in limbo.");
        builder.append("\n&6&l»&7 /celest cache").hover("&6Print cache");
        builder.append("\n&6&l»&7 /celest user <uuid/username>").hover("&6Print userdata");
        builder.append("\n&6&l»&7 /celest address <address>").hover("&6Print addressdata");

        MessageUtils.message(source, builder::build);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<String> optionalCommand = arguments.get(String.class);
        Optional<String> optionalArgument = arguments.get(String.class);

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
            case "limbo":
                MessageBuilder builder = new MessageBuilder();

                for (Limbo<?> limbo : Limbo.getLimbos()) {
                    if (builder.length() > 0)
                        builder.append(" ");

                    builder.append(limbo.getObject().toString()).hover(limbo.toString());
                }

                if (builder.length() == 0)
                    builder.append("&6&l»&7 No objects in limbo.");

                MessageUtils.message(source, builder::build);
                break;
            case "cache":
                CacheData[] data = Storage.read("cache", Storage.Directory.PARENT, CacheData[].class);

                MessageUtils.message(source, gson.toJson(data));
                break;
            case "user":
                if (optionalArgument.isPresent()) {
                    PlayerProfile profile = ProfileUtils.fromUsernameOrUuid(optionalArgument.get());
                    OfflineUser offline = OfflineUser.getOfflineUser(profile);

                    MessageUtils.message(source, gson.toJson(offline.getData()));
                    break;
                }
            case "address":
                if (optionalArgument.isPresent()) {
                    Address address = Address.getAddress(optionalArgument.get(), true);

                    MessageUtils.message(source, gson.toJson(address.getData()));
                    break;
                }
            default:
                sendHelpMessage(source);
        }
    }

}
