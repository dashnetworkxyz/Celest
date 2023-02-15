/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
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