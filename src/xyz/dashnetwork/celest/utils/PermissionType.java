/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.function.Predicate;

public enum PermissionType {

    GLOBAL(user -> true),
    STAFF(User::isStaff),
    ADMIN(User::isAdmin),
    OWNER(User::isOwner);

    private final Predicate<User> permission;

    PermissionType(Predicate<User> permission) { this.permission = permission; }

    public Predicate<User> getPermission() { return permission; }

    public boolean hasPermission(CommandSource source) {
        User user = CastUtils.toUser(source);

        if (user != null)
            return permission.test(user);

        return true;
    }

}
