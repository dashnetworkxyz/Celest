/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat;

import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.CastUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.function.Function;
import java.util.function.Predicate;

public final class MessageUtils {

    private static final ProxyServer server = Celest.getServer();
    private static final ConsoleCommandSource consoleCommandSource = server.getConsoleCommandSource();

    public static void message(@NotNull Audience audience, @NotNull String message) {
        message(audience, ComponentUtils.fromLegacyString(message));
    }

    public static void message(@NotNull Audience audience, @NotNull Component component) {
        audience.sendMessage(component);
    }

    public static void message(@NotNull Audience audience, @NotNull Function<User, Component> function) {
        message(audience, function.apply(CastUtils.toUser(audience)));
    }

    public static void broadcast(@NotNull String message) { broadcast(ComponentUtils.fromLegacyString(message)); }

    public static void broadcast(@NotNull Component component) { server.sendMessage(component); }

    public static void broadcast(@NotNull Function<@Nullable User, Component> function) {
        broadcast(true, function);
    }

    public static void broadcast(@NotNull Predicate<User> predicate, @NotNull String message) {
        broadcast(true, predicate, message);
    }

    public static void broadcast(@NotNull Predicate<User> predicate, @NotNull Component component) {
        broadcast(true, predicate, component);
    }

    public static void broadcast(@NotNull Predicate<User> predicate, @NotNull Function<@Nullable User, Component> function) {
        broadcast(true, predicate, function);
    }

    public static void broadcast(boolean console, @NotNull String message) {
        broadcast(console, user -> true, message);
    }

    public static void broadcast(boolean console, @NotNull Component component) {
        broadcast(console, user -> true, user -> component);
    }

    public static void broadcast(boolean console, @NotNull Function<@Nullable User, Component> function) {
        broadcast(console, user -> true, function);
    }

    public static void broadcast(boolean console, @NotNull Predicate<User> predicate, @NotNull String message) {
        broadcast(console, predicate, ComponentUtils.fromLegacyString(message));
    }

    public static void broadcast(boolean console, @NotNull Predicate<User> predicate, @NotNull Component component) {
        broadcast(console, predicate, user -> component);
    }

    public static void broadcast(boolean console, @NotNull Predicate<User> predicate, @NotNull Function<@Nullable User, Component> function) {
        for (User user : User.getUsers())
            if (predicate.test(user))
                message(user.getPlayer(), function.apply(user));

        if (console)
            message(consoleCommandSource, function.apply(null));
    }

}
