/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.command.arguments.parser.parsers.OfflinePlayerParser;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;

import java.util.Optional;

public final class CommandTest extends CelestCommand {

    public CommandTest() {
        super("test");

        setPermission(User::isOwner, true);
        addArguments(ArgumentType.OFFLINE_PLAYER);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<PlayerProfile> optional = arguments.get(PlayerProfile.class);

        MessageBuilder builder = new MessageBuilder();
        builder.append("&a");
        builder.append("test");
        builder.append("test");

        if (optional.isPresent()) {
            PlayerProfile profile = optional.get();

            builder.append("\nname: " + profile.getUsername());
            builder.append("\nuuid: " + profile.getUuid());
        }

        Component component = builder.build(null);
        String json = GsonComponentSerializer.gson().serialize(component);

        MessageUtils.message(source, component);
        MessageUtils.message(source, json);
    }

}
