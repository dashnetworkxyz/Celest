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

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import xyz.dashnetwork.celest.utils.VersionUtils;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;

public final class PreLoginListener {

    @Subscribe
    public void onPreLogin(PreLoginEvent event) {
        if (VersionUtils.isLegacy(event.getConnection().getProtocolVersion())) {
            event.setResult(PreLoginEvent.PreLoginComponentResult.denied(ComponentUtils.fromLegacyString(
                    "&6&lDashNetwork" +
                            "\n&61.7&7 is no longer supported." +
                            "\nPlease update to &61.8 or newer."
            )));
        }
    }

}
