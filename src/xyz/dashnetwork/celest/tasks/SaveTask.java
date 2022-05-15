/*
 * Copyright (c) 2022. Andrew Bell.
 * All rights reserved.
 */

package xyz.dashnetwork.celest.tasks;

import xyz.dashnetwork.celest.User;

public class SaveTask implements Runnable {

    @Override
    public void run() {
        for (User user : User.getUsers())
            user.save();
    }

}
