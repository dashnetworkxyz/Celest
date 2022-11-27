/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public final class PermissionUtils {

    public static boolean checkSource(@NotNull CommandSource source, @NotNull Predicate<User> predicate, boolean console) {
        User user = CastUtils.toUser(source);

        if (user != null)
            return predicate.test(user);

        return console;
    }

    public static boolean checkSource(@NotNull CommandSource source, @NotNull Predicate<User> predicate, @NotNull String permission) {
        User user = CastUtils.toUser(source);

        if (user != null && predicate.test(user))
            return true;

        return source.hasPermission(permission);
    }

    public static boolean checkVanished(@NotNull User user, @NotNull User check) {
        return user.isStaff() || !check.getData().getVanish() || user.getData().getVanish();
    }

}
