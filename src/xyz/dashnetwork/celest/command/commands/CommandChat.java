/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.Command;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.ArrayUtils;
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;

import java.util.List;

public final class CommandChat extends Command {

    public CommandChat() {
        super("chat");

        arguments(1, ArgumentType.CHAT_TYPE, ArgumentType.PLAYER_LIST);
        permission(ChatType.GLOBAL::hasPermission, true);
    }

    @Override
    protected void execute(CommandSource source, Arguments arguments) {
        ChatType type = arguments.getChatType();
        List<Player> list;

        // TODO: Util for this if if-else else. We'll need it in other places.
        if (arguments.size() > 1)
            list = arguments.getPlayerList();
        else if (source instanceof Player)
            list = List.of((Player) source);
        else {
            // TODO: Console
            MessageUtils.message(source, "hi console");
            return;
        }

        MessageUtils.message(source, "Chat Type: " + type);
        MessageUtils.message(source, "Players: " + ArrayUtils.convertToString(list.toArray(Player[]::new), Player::getUsername, ", "));

    }

}
