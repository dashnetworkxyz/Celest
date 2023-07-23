/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils;

public enum TimeType {

    SECOND(1000, "s", "sec", "second"),
    MINUTE(SECOND.toMillis(60), "m", "min", "minute"),
    HOUR(MINUTE.toMillis(60), "h", "hour"),
    DAY(HOUR.toMillis(24), "d", "day"),
    WEEK(DAY.toMillis(7), "w", "week"),
    MONTH(DAY.toMillis(30), "month"),
    YEAR(MONTH.toMillis(12), "y", "year");

    private final long time;
    private final String[] selector;

    TimeType(long time, String... selector) {
        this.time = time;
        this.selector = selector;
    }

    public long toMillis() { return time; }

    public long toMillis(long multiplier) { return time * multiplier; }

    public String[] getSelectors() { return selector; }

}
