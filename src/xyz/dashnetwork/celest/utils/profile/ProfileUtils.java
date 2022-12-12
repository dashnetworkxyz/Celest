/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.profile;

import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.utils.storage.Cache;
import xyz.dashnetwork.celest.utils.storage.Storage;
import xyz.dashnetwork.celest.utils.storage.data.CacheData;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.UUID;

public final class ProfileUtils {

    public static PlayerProfile fromUsername(@NotNull String username) {
        CacheData data = Cache.fromUsername(username, true);

        if (data != null)
            return new PlayerProfile(data.getUUID(), data.getUsername());

        return MojangUtils.fromUsername(username);
    }

    public static PlayerProfile fromUuid(@NotNull UUID uuid) {
        CacheData cacheData = Cache.fromUuid(uuid, true);

        if (cacheData != null)
            return new PlayerProfile(cacheData.getUUID(), cacheData.getUsername());

        UserData userData = Storage.read(uuid.toString(), Storage.Directory.USER, UserData.class);

        if (userData != null) {
            Cache.generate(uuid, userData);

            return new PlayerProfile(uuid, userData.getUsername());
        }

        return MojangUtils.fromUuid(uuid);
    }

    // TODO: Move this to Cache?
    public static PlayerProfile findMostRecent(@NotNull PlayerProfile... profiles) {
        long time = -1;
        CacheData selected = null;

        for (CacheData data : Cache.fromPlayerProfiles(profiles)) {
            long compare = data.getAccessTime();

            if (time < compare) {
                selected = data;
                time = compare;
            }
        }

        if (selected == null)
            return null;

        return new PlayerProfile(selected.getUUID(), selected.getUsername());
    }

}
