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
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.connection.User;

public final class CommandTest extends CelestCommand {

    public CommandTest() {
        super("test");

        setPermission(User::isOwner, true);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&a");
        builder.append("test");
        builder.append("test");

        Component component = builder.build(null);
        String json = GsonComponentSerializer.gson().serialize(component);

        MessageUtils.message(source, component);
        MessageUtils.message(source, json);
    }

}
