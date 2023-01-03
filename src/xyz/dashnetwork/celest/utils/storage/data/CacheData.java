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
