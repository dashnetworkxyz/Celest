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
import xyz.dashnetwork.celest.utils.ArgumentUtils;
import xyz.dashnetwork.celest.utils.ArrayUtils;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.chat.Messages;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerListFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class CommandChat extends CelestCommand {

    public CommandChat() {
        super("chat");

        setPermission(ChatType.GLOBAL::hasPermission, true);
        addArguments(ArgumentType.CHAT_TYPE);
        addArguments(User::isAdmin, true, ArgumentType.PLAYER_LIST);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<ChatType> optional = arguments.get(ChatType.class);
        List<Player> players = ArgumentUtils.playerListOrSelf(source, arguments);

        if (optional.isEmpty() || players.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        NamedSource named = NamedSource.of(source);
        ChatType chatType = optional.get();
        Predicate<User> sendSelf = user -> !user.getPlayer().equals(source);
        MessageBuilder builder;

        for (Player player : players) {
            User user = User.getUser(player);
            user.getData().setChatType(chatType);

            builder = new MessageBuilder();
            builder.append("&6&l»&7 You have been moved to &6" + chatType.getName() + "Chat");
            builder.append("&7 by ").onlyIf(sendSelf);
            builder.append(new PlayerFormat(named)).onlyIf(sendSelf);

            MessageUtils.message(player, builder::build);
        }

        if (source instanceof Player) {
            players.remove(source);

            if (players.size() > 0) {
                builder = new MessageBuilder();
                builder.append("&6&l»&7 You have moved ");
                builder.append(new PlayerListFormat(players));
                builder.append("&7 to &6" + chatType.getName() + "Chat");

                MessageUtils.message(source, builder::build);
            }
        }
    }

}
