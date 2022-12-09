/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.ArrayUtils;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;

import java.util.Optional;

public final class CommandChat extends CelestCommand {

    public CommandChat() {
        super("chat");

        setPermission(ChatType.GLOBAL::hasPermission, true);
        addArguments(ArgumentType.CHAT_TYPE);
        addArguments(User::isAdmin, true, ArgumentType.PLAYER_ARRAY);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<ChatType> chatType = arguments.get(ChatType.class);
        Optional<Player[]> playerArray = arguments.get(Player[].class);

        if (chatType.isEmpty()) {
            MessageUtils.message(source, "bro");
            return;
        }

        if (playerArray.isEmpty()) {
            // TODO: Console
            MessageUtils.message(source, "bro x2");
            return;
        }

        MessageUtils.message(source, "Chat Type: " + chatType.get());
        MessageUtils.message(source, "Players: " + ArrayUtils.convertToString(playerArray.get(), Player::getUsername, ", "));

    }

}
