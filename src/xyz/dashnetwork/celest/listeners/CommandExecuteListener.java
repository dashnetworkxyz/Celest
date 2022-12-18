/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;

public final class CommandExecuteListener {

    @Subscribe
    public void onCommandExecute(CommandExecuteEvent event) {
        NamedSource named = NamedSource.of(event.getCommandSource());

        MessageBuilder builder = new MessageBuilder();
        builder.append("&9&lCmd&r ");
        builder.append(new NamedSourceFormat(named));
        builder.append("&r &b&l»&b /" + event.getCommand());

        MessageUtils.broadcast(user -> user.getData().getCommandSpy(), builder::build);
    }

}
