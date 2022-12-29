/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;

public final class CommandExecuteListener {

    @Subscribe(order = PostOrder.FIRST)
    public void onCommandExecute(CommandExecuteEvent event) {
        CommandSource source = event.getCommandSource();

        if (source instanceof Player) {
            User user = User.getUser((Player) source);

            if (!user.isAuthenticated()) {
                event.setResult(CommandExecuteEvent.CommandResult.denied());

                MessageUtils.message(source, "&6&l»&7 Please enter your 2fa code into chat.");
                return;
            }
        }

        NamedSource named = NamedSource.of(source);

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&r ");
        builder.append(new NamedSourceFormat(named));
        builder.append("&r &b&l»&b /" + event.getCommand());

        MessageUtils.broadcast(user -> user.getData().getCommandSpy(), builder::build);
    }

}
