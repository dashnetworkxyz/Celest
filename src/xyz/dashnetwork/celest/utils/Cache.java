/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved 
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium 
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.proxy.Player;

import java.util.*;

public class Cache {

    // TODO: Punish system

    private static final List<CacheData> cache = new ArrayList<>();

    public static List<CacheData> getCache() { return cache; }

    public static void load() {
        CacheData[] cacheArray = Storage.read("cache", Storage.Directory.PARENT, CacheData[].class);

        if (cacheArray != null)
            cache.addAll(Arrays.asList(cacheArray));
    }

    public static void save() {
        if (!cache.isEmpty())
            Storage.write("cache", Storage.Directory.PARENT, cache.toArray(new CacheData[0]));
    }

    public static void generate(Player player) {
        UUID uuid = player.getUniqueId();
        String username = player.getUsername();
        String address = player.getRemoteAddress().getHostString();

        cache.removeIf(data -> data.getUUID().equals(uuid));
        cache.add(new CacheData(uuid, username, address));
    }

    public static CacheData fromUuid(UUID uuid) {
        for (CacheData each : cache)
            if (each.getUUID().equals(uuid))
                return each;
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
