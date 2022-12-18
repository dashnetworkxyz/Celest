/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;

import java.nio.ByteBuffer;
import java.util.UUID;

public final class CommandUniqueId extends CelestCommand {

    public CommandUniqueId() {
        super("uniqueid", "uuid");

        addArguments(ArgumentType.UNIQUE_ID);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        UUID uuid = arguments.get(UUID.class).orElse(UUID.randomUUID());

        long most = uuid.getMostSignificantBits();
        long least = uuid.getLeastSignificantBits();
        byte[] bytes = ByteBuffer.allocate(16).putLong(most).putLong(least).array();
        int[] ints = new int[4];

        for (int i = 0; i < 4; i++)
            ints[i] = ByteBuffer.wrap(bytes, i * 4, 4).getInt();

        String stringUuid = uuid.toString();
        String stringLongs = "UUIDMost:" + most + ",UUIDLeast:" + least;
        String stringInts = "Id:[I;" + ints[0] + "," + ints[1] + "," + ints[2] + "," + ints[3] + "]";

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&6 " + stringUuid).hover("&7Click to copy &6" + stringUuid).click(ClickEvent.suggestCommand(stringUuid));
        builder.append("\n&6&l»&7 " + stringLongs).hover("&7Click to copy &6" + stringLongs).click(ClickEvent.suggestCommand(stringLongs));
        builder.append("\n&6&l»&7 " + stringInts).hover("&7Click to copy &6" + stringInts).click(ClickEvent.suggestCommand(stringInts));

        MessageUtils.message(source, builder::build);
    }

}
