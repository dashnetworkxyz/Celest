/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import java.text.SimpleDateFormat;

public class TimeUtils {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy, hh:mm a z");

    public static String longToDate(long time) { return formatter.format(time); }

}
