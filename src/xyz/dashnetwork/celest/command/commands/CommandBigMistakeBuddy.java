/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Optional;

public class CommandBigMistakeBuddy extends CelestCommand {

    public CommandBigMistakeBuddy() {
        super("bigmistakebuddynowwehaveyourgamefiles", "bigmistakebuddy", "bmb");

        setPermission(User::isOwner, true);
        addArguments(ArgumentType.PLAYER_LIST);
    }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        Optional<Player[]> optional = arguments.get(Player[].class);

        if (optional.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        Component title = ComponentUtils.toComponent("&6Big mistake, buddy");
        Component subtitle = ComponentUtils.toComponent("&cNow we have your game files");

        for (Player player : optional.get()) {
            player.sendTitlePart(TitlePart.SUBTITLE, subtitle);
            player.sendTitlePart(TitlePart.TITLE, title);
        }
    }

}
