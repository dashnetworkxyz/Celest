/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.vault.api;

import com.velocitypowered.api.proxy.Player;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import xyz.dashnetwork.celest.vault.Vault;

import java.util.UUID;

public final class LuckAPI implements Vault {

    private final UserManager manager = LuckPermsProvider.get().getUserManager();

    @Override
    public String getPrefix(Player player) {
        User user = getUser(player.getUniqueId());
        String prefix = user.getCachedData().getMetaData().getPrefix();

        if (prefix == null)
            prefix = "";

        return prefix;
    }

    @Override
    public String getSuffix(Player player) {
        User user = getUser(player.getUniqueId());
        String suffix = user.getCachedData().getMetaData().getSuffix();

        if (suffix == null)
            suffix = "";

        return suffix;
    }

    private User getUser(UUID uuid) {
        if (!manager.isLoaded(uuid))
            manager.loadUser(uuid);

        return manager.getUser(uuid);
    }

}
