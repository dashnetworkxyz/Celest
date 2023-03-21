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

package xyz.dashnetwork.celest.utils.profile;

import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.storage.Cache;
import xyz.dashnetwork.celest.utils.storage.Storage;
import xyz.dashnetwork.celest.utils.storage.data.CacheData;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.UUID;

public final class ProfileUtils {

    public static PlayerProfile fromUsernameOrUuid(@NotNull String string) {
        if (StringUtils.matchesUuid(string))
            return ProfileUtils.fromUuid(UUID.fromString(string));
        else
            return ProfileUtils.fromUsername(string);
    }

    public static PlayerProfile fromUsername(@NotNull String username) {
        CacheData data = Cache.fromUsername(username, true);

        if (data != null)
            return new PlayerProfile(data.getUUID(), data.getUsername());

        PlayerProfile profile = MojangUtils.fromUsername(username);

        if (profile == null) {
            String uuid = Storage.read(username.toLowerCase(), Storage.Directory.LOOKUP, String.class);

            if (uuid != null && StringUtils.matchesUuid(uuid))
                profile = MojangUtils.fromUuid(UUID.fromString(uuid));
        }

        if (profile == null)
            return null;

        UUID uuid = profile.uuid();
        UserData userData = Storage.read(uuid.toString(), Storage.Directory.USER, UserData.class);

        if (userData != null)
            Cache.generate(uuid, profile.username(), userData.getAddress());

        return profile;
    }

    public static PlayerProfile fromUuid(@NotNull UUID uuid) {
        CacheData cacheData = Cache.fromUuid(uuid, true);

        if (cacheData != null)
            return new PlayerProfile(cacheData.getUUID(), cacheData.getUsername());

        UserData userData = Storage.read(uuid.toString(), Storage.Directory.USER, UserData.class);

        if (userData != null) {
            Cache.generate(uuid, userData.getUsername(), userData.getAddress());

            return new PlayerProfile(uuid, userData.getUsername());
        }

        return MojangUtils.fromUuid(uuid);
    }

}
