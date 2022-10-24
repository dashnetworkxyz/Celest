/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat;

import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.User;

import java.util.function.Predicate;

public final class MessageUtils {

    private static final ProxyServer server = Celest.getServer();

    public static void message(@NotNull Audience audience, @NotNull String message) { message(audience, ColorUtils.toComponent(message)); }

    public static void message(@NotNull Audience audience, @NotNull Component component) { audience.sendMessage(component); }

    public static void broadcast(@NotNull String message) { broadcast(ColorUtils.toComponent(message)); }

    public static void broadcast(@NotNull Component component) { server.sendMessage(component); }

    public static void broadcast(@NotNull Predicate<User> predicate, @NotNull String message) { broadcast(predicate, ColorUtils.toComponent(message)); }

    public static void broadcast(@NotNull Predicate<User> predicate, @NotNull Component component) {
        for (User user : User.getUsers())
            if (predicate.test(user))
                user.getPlayer().sendMessage(component);

        server.getConsoleCommandSource().sendMessage(component);
    }

}
