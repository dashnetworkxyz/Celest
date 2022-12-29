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
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.OfflineUser;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;

import java.util.Optional;
import java.util.UUID;

public final class CommandBan extends CelestCommand {

    public CommandBan() {
        super("ban");

        setPermission(User::isAdmin, true);
        addArguments(ArgumentType.OFFLINE_USER);
        addArguments(ArgumentType.MESSAGE);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<OfflineUser> optionalOffline = arguments.get(OfflineUser.class);
        Optional<String> optionalString = arguments.get(String.class);

        if (optionalOffline.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        OfflineUser offline = optionalOffline.get();
        String reason = optionalString.orElse("No reason provided.");
        UUID uuid = null;

        if (source instanceof Player)
            uuid = ((Player) source).getUniqueId();

        offline.getData().setBan(new PunishData(uuid, reason, null));
        offline.save();

        NamedSource named = NamedSource.of(source);
        MessageBuilder builder;

        if (offline instanceof User) {
            builder = new MessageBuilder();
            builder.append("&6&lDashNetwork");
            builder.append("\n&7You have been permanently banned");
            builder.append("\n&7You were banned by &6" + named.getUsername());
            builder.append("\n\n" + reason);

            ((User) offline).getPlayer().disconnect(builder.build(null));
        }

        builder = new MessageBuilder();
        builder.append("&6&l»&r ");
        builder.append("&6" + offline.getUsername() + "&7 permanently banned by")
                        .hover("&6Reason:\n&7" + reason);
        builder.append(" ");
        builder.append(new NamedSourceFormat(named));

        MessageUtils.broadcast(User::isStaff, builder::build);
    }

}
