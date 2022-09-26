/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

public enum TimeType {

    SECOND('s', 1000),
    MINUTE('m', SECOND.toLong() * 60),
    HOUR('h', MINUTE.toLong() * 60),
    DAY('d', HOUR.toLong() * 24),
    WEEK('w', DAY.toLong() * 7),
    MONTH('m', DAY.toLong() * 30),
    YEAR('y', MONTH.toLong() * 12);

    private final char selector;
    private final long time;

    TimeType(char selector, long time) {
        this.selector = selector;
        this.time = time;
    }

    public long toLong() { return time; }

    public char getSelector() { return selector; }

}
