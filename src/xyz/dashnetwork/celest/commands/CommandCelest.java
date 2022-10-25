/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.utils.PredicateUtils;
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.Messages;
import xyz.dashnetwork.celest.utils.data.UserData;
import xyz.dashnetwork.celest.utils.storage.LegacyParser;
import xyz.dashnetwork.celest.utils.storage.Storage;

import java.util.List;

public final class CommandCelest implements SimpleCommand {

    @Override
    public boolean hasPermission(Invocation invocation) {
        return PredicateUtils.permission(User::isOwner, true, invocation.source());
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        int length = args.length;

        if (length == 0) {
            MessageUtils.message(source, Messages.commandCelestHelp());
            return;
        }

        // TODO

        switch (args[0].toLowerCase()) {
            case "legacy-data-import": // intentionally annoying argument as this can overwrite modern data.
                LegacyParser parser = new LegacyParser();

                MessageUtils.message(source, "&6&l» &7Reading legacy data...");
                parser.read();

                MessageUtils.message(source, "&6&l» &7Writing legacy data to modern data...");
                parser.write();

                MessageUtils.message(source, "&6&l» &7Legacy data import complete!");
                return;
            case "userdata":
                if (length == 1) {
                    MessageUtils.message(source, "&6&l» &7Reading userdata...");

                    List<UserData> data = Storage.readAll(Storage.Directory.USER, UserData.class);

                    MessageUtils.message(source, "&6&l» &7Found &6" + data.size() + " &7entries.");
                } else {
                    UserData data = Storage.read(args[1], Storage.Directory.USER, UserData.class);

                    if (data == null) {
                        MessageUtils.message(source, "&6&l» &7Failed to read &6celest/userdata/" + args[1] + ".json");
                        return;
                    }

                    Component message = Messages.userdataInfo(args[1], data.getAddress(), data.getUsername(), data.getNickname(),
                            data.getBan() + "", data.getMute() + "", data.getChatType().name(),
                            data.getLastPlayed() + "", data.getAltSpy() + "", data.getCommandSpy() + "",
                            data.getPingSpy() + "", data.getVanish() + "");

                    MessageUtils.message(source, message);
                }

                return;
            case "addressdata":
                return;
            default:
                MessageUtils.message(source, Messages.commandCelestHelp());
        }
    }

}
