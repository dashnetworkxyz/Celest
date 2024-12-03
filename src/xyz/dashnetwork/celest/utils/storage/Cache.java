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

package xyz.dashnetwork.celest.utils.storage;

import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.utils.TimeType;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.storage.data.CacheData;
import xyz.dashnetwork.celest.utils.storage.data.PlayerData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Cache {

    private static final List<CacheData> cache = new CopyOnWriteArrayList<>();

    public static List<CacheData> getCache() { return cache; }

    public static void load() {
        CacheData[] cacheArray = Storage.read("cache", Storage.Directory.PARENT, CacheData[].class);

        if (cacheArray != null)
            cache.addAll(List.of(cacheArray));
    }

    public static void save() {
        if (!cache.isEmpty())
            Storage.write("cache", Storage.Directory.PARENT, cache.toArray(CacheData[]::new));
    }

    public static void removeOldEntries() {
        cache.removeIf(data -> System.currentTimeMillis() - TimeType.MONTH.toMillis() >= data.getAccessTime());
    }

    public static void refreshOnline() {
        for (User user : User.getUsers())
            fromUuid(user.getUuid(), true);
    }

    public static void generate(UUID uuid, String username, String address) {
        cache.removeIf(each -> each.getUUID().equals(uuid) || each.getUsername().equalsIgnoreCase(username));
        cache.add(new CacheData(uuid, username, address));
    }

    public static CacheData fromUuid(UUID uuid, boolean updateAccessTime) {
        for (CacheData each : cache) {
            if (each.getUUID().equals(uuid)) {
                if (updateAccessTime)
                    each.setAccessTime(System.currentTimeMillis());

                return each;
            }
        }
        return null;
    }

    public static CacheData fromUsername(String username, boolean updateAccessTime) {
        for (CacheData each : cache) {
            if (each.getUsername().equalsIgnoreCase(username)) {
                if (updateAccessTime)
                    each.setAccessTime(System.currentTimeMillis());

                return each;
            }
        }
        return null;
    }

    public static List<CacheData> fromAddress(String address) {
        List<CacheData> list = new ArrayList<>();

        for (CacheData each : cache)
            if (each.getAddress().equals(address))
                list.add(each);

        return list;
    }

    public static List<CacheData> fromPlayerProfiles(PlayerData... profiles) {
        List<CacheData> list = new ArrayList<>();

        for (PlayerData profile : profiles)
            for (CacheData each : cache)
                if (profile.uuid().equals(each.getUUID()))
                    list.add(each);

        return list;
    }

    public static CacheData findMostRecent(@NotNull PlayerData... profiles) {
        long time = -1;
        CacheData selected = null;

        for (CacheData data : fromPlayerProfiles(profiles)) {
            long compare = data.getAccessTime();

            if (time < compare) {
                selected = data;
                time = compare;
            }
        }

        return selected;
    }

}
