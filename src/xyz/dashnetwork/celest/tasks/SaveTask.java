/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.tasks;

import xyz.dashnetwork.celest.User;
import xyz.dashnetwork.celest.storage.Cache;

public class SaveTask implements Runnable {

    @Override
    public void run() {
        for (User user : User.getUsers())
            user.save();

        Cache.save();
    }

}
