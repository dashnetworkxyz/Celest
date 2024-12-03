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

package xyz.dashnetwork.celest.utils.profile;

import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.util.GameProfile;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.storage.Cache;
import xyz.dashnetwork.celest.utils.storage.Storage;
import xyz.dashnetwork.celest.utils.storage.data.CacheData;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.UUID;

public final class ProfileUtils {

    public static GameProfile fromUsernameOrUuid(@NotNull String string) {
        if (StringUtils.matchesUuid(string))
            return ProfileUtils.fromUuid(UUID.fromString(string));
        else
            return ProfileUtils.fromUsername(string);
    }

    public static GameProfile fromUsername(@NotNull String username) {
        CacheData data = Cache.fromUsername(username, true);

        if (data != null)
            return new GameProfile(data.getUUID(), data.getUsername(), ImmutableList.of());

        String stringUuid = Storage.read(username.toLowerCase(), Storage.Directory.LOOKUP, String.class);
        GameProfile profile = null;

        if (stringUuid != null && StringUtils.matchesUuid(stringUuid))
            profile = MojangUtils.fromUuid(UUID.fromString(stringUuid));

        if (profile == null)
            profile = MojangUtils.fromUsername(username);

        if (profile == null)
            return null;

        UUID uuid = profile.getId();
        UserData userData = Storage.read(uuid.toString(), Storage.Directory.USER, UserData.class);

        if (userData != null)
            Cache.generate(uuid, profile.getName(), userData.getAddress());

        return profile;
    }

    public static GameProfile fromUuid(@NotNull UUID uuid) {
        CacheData cacheData = Cache.fromUuid(uuid, true);

        if (cacheData != null)
            return new GameProfile(cacheData.getUUID(), cacheData.getUsername(), ImmutableList.of());

        UserData userData = Storage.read(uuid.toString(), Storage.Directory.USER, UserData.class);

        if (userData != null) {
            Cache.generate(uuid, userData.getUsername(), userData.getAddress());

            return new GameProfile(uuid, userData.getUsername(), ImmutableList.of());
        }

        return MojangUtils.fromUuid(uuid);
    }

}
