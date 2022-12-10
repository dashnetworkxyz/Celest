/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.connection.Address;
import xyz.dashnetwork.celest.utils.connection.User;

public interface NamedSource {

    NamedSource console = new NamedSource() {
        @Override
        public String getDisplayname() { return "&4&lServer&6 Velocity"; }

        @Override
        public String getUsername() { return VersionUtils.getProxyName() + " " + VersionUtils.getProxyVersion(); }

        @Override
        public Address getAddress() { return null; }
    };

    static NamedSource of(CommandSource source) {
        if (source instanceof Player)
            return User.getUser((Player) source);

        return console;
    }

    String getDisplayname();

    String getUsername();

    Address getAddress();

}
