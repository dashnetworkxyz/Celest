/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.storage.data;

import java.util.UUID;

public final class PunishData {

    private final UUID judge;
    private final String reason;
    private final Long expiration;

    public PunishData(UUID judge, String reason, Long expiration) {
        this.judge = judge;
        this.reason = reason;
        this.expiration = expiration;
    }

    public UUID getJudge() { return judge; }

    public String getReason() { return reason; }

    public Long getExpiration() { return expiration; }

}
