/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.tasks;

import xyz.dashnetwork.celest.utils.Limbo;
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.storage.Cache;

public final class SaveTask implements Runnable {

    @Override
    public void run() {
        for (User user : User.getUsers())
            user.save();

        for (Limbo<?> limbo : Limbo.getLimbos())
            limbo.save();

        Cache.save();
    }

}
