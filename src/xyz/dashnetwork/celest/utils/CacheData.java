/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import java.util.UUID;

public class CacheData {

    private UUID uuid;
    private String username, address;
    private long date;

    public CacheData(UUID uuid, String username, String address) {
        this.uuid = uuid;
        this.username = username;
        this.address = address;
        this.date = System.currentTimeMillis(); // TODO: Test this isn't overwritten by Gson
    }

    public UUID getUUID() { return uuid; }

    public String getUsername() { return username; }

    public String getAddress() { return address; }

    public long getDate() { return date; }

}
