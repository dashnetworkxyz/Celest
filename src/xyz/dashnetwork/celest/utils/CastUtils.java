/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.connection.User;

public final class CastUtils {

    public static User toUser(Object object) {
        if (object instanceof Player)
            return User.getUser((Player) object);

        return null;
    }

}
