/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
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
