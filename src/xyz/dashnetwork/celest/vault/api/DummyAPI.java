/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.vault.api;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.User;
import xyz.dashnetwork.celest.vault.Vault;

public final class DummyAPI implements Vault {

    @Override
    public String getPrefix(Player player) {
        User user = User.getUser(player);

        if (user.isOwner())
            return "&4&lOwner&6";
        else if (user.isAdmin())
            return "&4&lAdmin&6";
        else if (user.isStaff())
            return "&1&lMod&6";
        else
            return "&2&lMember&7";
    }

    @Override
    public String getSuffix(Player player) { return ""; }

}
