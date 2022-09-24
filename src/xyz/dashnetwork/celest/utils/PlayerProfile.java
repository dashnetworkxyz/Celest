/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import java.util.UUID;

public class PlayerProfile {

    private final UUID uuid;
    private final String username;

    public PlayerProfile(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    public UUID getUuid() { return uuid; }

    public String getUsername() { return username; }

}
