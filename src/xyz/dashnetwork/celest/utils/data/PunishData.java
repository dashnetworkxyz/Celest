/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.data;

import java.util.UUID;

public final class PunishData {

    private final UUID banner;
    private final String reason;
    private final long expiration;

    public PunishData(UUID banner, String reason, long expiration) {
        this.banner = banner;
        this.reason = reason;
        this.expiration = expiration;
    }

    // TODO: Better name?
    public UUID getBanner() { return banner; }

    public String getReason() { return reason; }

    public long getExpiration() { return expiration; }

}
