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
        if (source instanceof Player)
            return predicate.test(User.getUser((Player) source));

        return console;
    }

    public static void filterVanished(@NotNull User user, @NotNull List<Player> players) {
        if (user.isStaff() || user.getData().getVanish())
            return;

        players.removeIf(player -> User.getUser(player).getData().getVanish());
    }

}
