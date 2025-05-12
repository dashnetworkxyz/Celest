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

package xyz.dashnetwork.celest.command.arguments.parsers;

import com.velocitypowered.api.util.GameProfile;
import xyz.dashnetwork.celest.command.arguments.Parser;
import xyz.dashnetwork.celest.util.StringUtil;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.util.ProfileUtil;

import java.util.UUID;

public final class OfflineUserParser implements Parser {

    @Override
    public Object parse(User user, String input) {
        if (input.matches("@[PpSs]") && user != null)
            return user;

        GameProfile profile;

        if (StringUtil.matchesUuid(input))
            profile = ProfileUtil.fromUuid(UUID.fromString(input));
        else
            profile = ProfileUtil.fromUsername(input);

        if (profile == null)
            return null;

        return OfflineUser.getOfflineUser(profile);
    }

}
