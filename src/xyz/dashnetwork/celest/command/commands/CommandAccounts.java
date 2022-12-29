/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.OfflineUserFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerProfileFormat;
import xyz.dashnetwork.celest.utils.connection.Address;
import xyz.dashnetwork.celest.utils.connection.OfflineUser;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CommandAccounts extends CelestCommand {

    public CommandAccounts() {
        super("accounts", "alts");

        setPermission(User::isStaff, true);
        addArguments(ArgumentType.OFFLINE_USER);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<OfflineUser> optional = arguments.get(OfflineUser.class);

        if (optional.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        OfflineUser offline = optional.get();
        Address address = Address.getAddress(offline.getData().getAddress(), true);
        List<PlayerProfile> profiles = new ArrayList<>(List.of(address.getData().getProfiles()));

        profiles.removeIf(profile -> profile.getUuid().equals(offline.getUuid()));

        MessageBuilder builder = new MessageBuilder();

        if (profiles.isEmpty()) {
            builder.append("&6&l»&7 No known alts for ");
            builder.append(new OfflineUserFormat(offline)).prefix("&6");
        } else {
            builder.append("&6&l»&7 Known alts for ");
            builder.append(new OfflineUserFormat(offline)).prefix("&6");
            builder.append("&7: ");
            builder.append(new PlayerProfileFormat(profiles)).prefix("&6");
        }

        MessageUtils.message(source, builder::build);
    }

}
