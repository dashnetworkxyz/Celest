/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.User;
import xyz.dashnetwork.celest.utils.MessageUtils;
import xyz.dashnetwork.celest.utils.Messages;

public class CommandExecuteListener {

    @Subscribe
    public void onCommandExecute(CommandExecuteEvent event) {
        CommandSource source = event.getCommandSource();

        if (source instanceof Player) {
            Player player = (Player) source;
            String displayname = User.getUser(player).getDisplayname();
            String username = player.getUsername();
            String message = "/" + event.getCommand();

            Component component = Messages.playerCommandSpy(username, displayname, message);
            MessageUtils.broadcast(user -> user.getData().getCommandSpy(), component);
        }
    }

}
