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

package xyz.dashnetwork.celest.util;

import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.util.GameProfile;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.storage.Storage;
import xyz.dashnetwork.celest.storage.data.CacheData;
import xyz.dashnetwork.celest.storage.data.UserData;

import java.util.UUID;

public final class ProfileUtil {

    public static GameProfile fromUsernameOrUuid(@NotNull String string) {
        if (StringUtil.matchesUuid(string))
            return ProfileUtil.fromUuid(UUID.fromString(string));
        else
            return ProfileUtil.fromUsername(string);
    }

    public static GameProfile fromUsername(@NotNull String username) {
        CacheData data = Cache.fromUsername(username, true);

        if (data != null)
            return new GameProfile(data.getUUID(), data.getUsername(), ImmutableList.of());

        String stringUuid = Storage.read(username.toLowerCase(), Storage.Directory.LOOKUP, String.class);
        GameProfile profile = null;

        if (stringUuid != null && StringUtil.matchesUuid(stringUuid))
            profile = MojangUtil.fromUuid(UUID.fromString(stringUuid));

        if (profile == null)
            profile = MojangUtil.fromUsername(username);

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

        return MojangUtil.fromUuid(uuid);
    }

}
