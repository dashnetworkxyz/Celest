/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.tasks;

import xyz.dashnetwork.celest.Address;
import xyz.dashnetwork.celest.storage.Cache;

public final class ClearTask implements Runnable {

    @Override
    public void run() {
        Cache.removeOldEntries();
        Address.removeOldEntries();
    }

}
