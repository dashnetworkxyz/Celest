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

public final class CommandTempBan extends CelestCommand {

    public CommandTempBan() {
        super("tempban", "bantemp");

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

        offline.getData().setBan(new PunishData(uuid, reason, duration));

        NamedSource named = NamedSource.of(source);
        MessageBuilder builder;

        if (offline instanceof User) {
            builder = new MessageBuilder();
            builder.append("&6&lDashNetwork");
            builder.append("\n&7You have been temporarily banned");
            builder.append("\n&7You were banned by &6" + named.getUsername());
            builder.append("\n&7Your ban will expire on &6" + date);
            builder.append("\n\n" + reason);

            ((User) offline).getPlayer().disconnect(builder.build(null));
        }

        builder = new MessageBuilder();
        builder.append("&6&l»&r ");
        builder.append(new PlayerProfileFormat(offline)).prefix("&6");
        builder.append("&7 temporarily banned by ");
        builder.append(new NamedSourceFormat(named));
        builder.append("\n&6&l»&7 Hover here for details.")
                .hover("&7Judge: &6" + named.getUsername())
                .hover("\n&7Expiration: &6" + date)
                .hover("\n&7Reason: &6" + reason);

        MessageUtils.broadcast(User::isStaff, builder::build);
    }


}
