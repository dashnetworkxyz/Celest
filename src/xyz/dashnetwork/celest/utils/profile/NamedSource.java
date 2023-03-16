/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils.profile;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.connection.User;

public interface NamedSource {

    NamedSource console = new NamedSource() {
        @Override
        public String getDisplayname() { return "&4&lServer&6 Velocity"; }

        @Override
        public String getUsername() { return "Console"; }
    };

    static NamedSource of(CommandSource source) {
        if (source instanceof Player player)
            return User.getUser(player);

        return console;
    }

    String getDisplayname();

    String getUsername();

}
