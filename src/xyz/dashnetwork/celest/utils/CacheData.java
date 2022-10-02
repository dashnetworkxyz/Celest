/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import java.util.UUID;

public final class CacheData {

    private final UUID uuid;
    private final String username, address;
    private long accessTime;

    public CacheData(UUID uuid, String username, String address) {
        this.uuid = uuid;
        this.username = username;
        this.address = address;
        this.accessTime = System.currentTimeMillis();
    }

    public UUID getUUID() { return uuid; }

    public String getUsername() { return username; }

    public String getAddress() { return address; }

    public long getAccessTime() { return accessTime; }

    public void setAccessTime(long accessTime) { this.accessTime = accessTime; }

}
