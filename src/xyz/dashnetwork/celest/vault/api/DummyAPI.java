/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.vault.api;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.connection.User;
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
