/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.vault.api;

import com.velocitypowered.api.proxy.Player;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import xyz.dashnetwork.celest.vault.Vault;

import java.util.UUID;

public final class LuckPermsAPI implements Vault {

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
