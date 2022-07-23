/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import java.util.UUID;

public class PunishData {

    private UUID banner;
    private String reason;
    private long expiration;

    public PunishData(UUID banner, String reason, long expiration) {
        this.banner = banner;
        this.reason = reason;
        this.expiration = expiration;
    }

    public UUID getBanner() { return banner; }

    public String getReason() { return reason; }

    public long getExpiration() { return expiration; }

}
