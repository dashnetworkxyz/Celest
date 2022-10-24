/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved 
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium 
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.storage;

import xyz.dashnetwork.celest.utils.data.CacheData;
import xyz.dashnetwork.celest.utils.TimeType;
import xyz.dashnetwork.celest.utils.data.UserData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class Cache {

    private static final List<CacheData> cache = new ArrayList<>();

    public static List<CacheData> getCache() { return cache; }

    public static void load() {
        CacheData[] cacheArray = Storage.read("cache", Storage.Directory.PARENT, CacheData[].class);

        if (cacheArray != null)
            cache.addAll(Arrays.asList(cacheArray));
    }

    public static void save() {
        if (!cache.isEmpty())
            Storage.write("cache", Storage.Directory.PARENT, cache.toArray(CacheData[]::new));
    }

    public static void removeOldEntries() {
        cache.removeIf(data -> System.currentTimeMillis() - TimeType.MONTH.toLong() >= data.getAccessTime());
    }

    public static void generate(UUID uuid, UserData userData) {
        String username = userData.getUsername();
        String address = userData.getAddress();

        cache.removeIf(data -> data.getUUID().equals(uuid) || data.getUsername().equalsIgnoreCase(username));
        cache.add(new CacheData(uuid, username, address));
    }

    public static CacheData fromUuid(UUID uuid) {
        for (CacheData each : cache)
            if (each.getUUID().equals(uuid)) {
                each.setAccessTime(System.currentTimeMillis());
                return each;
            }
        return null;
    }

    public static CacheData fromUsername(String username) {
        for (CacheData each : cache)
            if (each.getUsername().equalsIgnoreCase(username)) {
                each.setAccessTime(System.currentTimeMillis());
                return each;
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

}
