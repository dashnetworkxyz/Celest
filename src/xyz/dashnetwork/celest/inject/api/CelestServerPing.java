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

package xyz.dashnetwork.celest.inject.api;

import com.velocitypowered.api.proxy.server.ServerPing;
import com.velocitypowered.api.util.Favicon;
import com.velocitypowered.api.util.ModInfo;
import net.kyori.adventure.text.Component;

public record CelestServerPing(
        ServerPing.Version version,
        ServerPing.Players players,
        Component description,
        Favicon favicon,
        ModInfo modinfo,
        boolean preventsChatReports
) {

    public CelestServerPing(ServerPing original) {
        this(
                original.getVersion(),
                original.getPlayers().orElse(null),
                original.getDescriptionComponent(),
                original.getFavicon().orElse(null),
                original.getModinfo().orElse(null),
                true
        );
    }

}
