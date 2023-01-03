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

package xyz.dashnetwork.celest.inject.api;

import com.velocitypowered.api.proxy.server.ServerPing;
import com.velocitypowered.api.util.Favicon;
import com.velocitypowered.api.util.ModInfo;
import net.kyori.adventure.text.Component;

public final class CelestServerPing {

    private final ServerPing.Version version;
    private final ServerPing.Players players;
    private final Component description;
    private final Favicon favicon;
    private final ModInfo modinfo;
    private final boolean preventsChatReports;

    public CelestServerPing(ServerPing original) {
        this.version = original.getVersion();
        this.players = original.getPlayers().orElse(null);
        this.description = original.getDescriptionComponent();
        this.favicon = original.getFavicon().orElse(null);
        this.modinfo = original.getModinfo().orElse(null);
        this.preventsChatReports = true;
    }

}
