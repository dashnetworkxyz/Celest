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
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;

public final class CommandDiscord extends CelestCommand {

    public CommandDiscord() { super("discord"); }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 Join the discord server at ");
        builder.append("&6https://discord.gg/3RDsNEE")
                .hover("&7Click to open &6https://discord.com/invite/3RDsNEE")
                .click(ClickEvent.openUrl("https://discord.com/invite/3RDsNEE"));

        MessageUtils.message(source, builder::build);
    }

}
