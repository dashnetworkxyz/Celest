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
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerProfileFormat;
import xyz.dashnetwork.celest.utils.connection.OfflineUser;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;

import java.util.Optional;
import java.util.UUID;

public final class CommandTempMute extends CelestCommand {

    public CommandTempMute() {
        super("tempmute", "mutetemp");

        setPermission(User::isAdmin, true);
        addArguments(ArgumentType.OFFLINE_USER, ArgumentType.LONG, ArgumentType.MESSAGE);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<OfflineUser> optionalOffline = arguments.get(OfflineUser.class);
        Optional<Long> optionalLong = arguments.get(Long.class);

        if (optionalOffline.isEmpty() || optionalLong.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        OfflineUser offline = optionalOffline.get();
        long duration = System.currentTimeMillis() + optionalLong.get();
        String date = TimeUtils.longToDate(duration);
        String reason = arguments.get(String.class).orElse("No reason provided.");
        UUID uuid = null;

        if (source instanceof Player)
            uuid = ((Player) source).getUniqueId();

        offline.getData().setMute(new PunishData(uuid, reason, duration));

        NamedSource named = NamedSource.of(source);

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&r ");
        builder.append(new PlayerProfileFormat(offline)).prefix("&6");
        builder.append("&7 temporarily muted by ");
        builder.append(new NamedSourceFormat(named));
        builder.append("\n&6&l»&7 Hover here for details.")
                .hover("&7Judge: &6" + named.getUsername())
                .hover("\n&7Expiration: &6" + date)
                .hover("\n&7Reason: &6" + reason);

        MessageUtils.broadcast(User::isStaff, builder::build);
    }

}
