/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.permission.PermissionSubject;
import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.User;

import java.util.function.Predicate;

public class PredicateUtils {

    public static boolean permission(@NotNull Predicate<User> predicate, boolean console, @NotNull PermissionSubject subject) {
        if (subject instanceof Player)
            return predicate.test(User.getUser((Player) subject));

        return console;
    }

}
