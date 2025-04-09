/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.chat;

import com.velocitypowered.api.proxy.ConsoleCommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.connection.User;

import java.util.function.Function;
import java.util.function.Predicate;

public final class MessageUtil {

    private static final ProxyServer server = Celest.getServer();
    private static final ConsoleCommandSource consoleCommandSource = server.getConsoleCommandSource();

    public static void message(@NotNull Audience audience, @NotNull String message) {
        message(audience, ComponentUtil.fromString(message));
    }

    public static void message(@NotNull Audience audience, @NotNull Component component) {
        audience.sendMessage(component);
    }

    public static void message(@NotNull Audience audience, @NotNull Function<@Nullable User, Component> function) {
        message(audience, function.apply(User.getUser(audience).orElse(null)));
    }

    public static void broadcast(@NotNull String message) { broadcast(ComponentUtil.fromString(message)); }

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
        broadcast(console, user -> true, component);
    }

    public static void broadcast(boolean console, @NotNull Function<@Nullable User, Component> function) {
        broadcast(console, user -> true, function);
    }

    public static void broadcast(boolean console, @NotNull Predicate<User> predicate, @NotNull String message) {
        broadcast(console, predicate, ComponentUtil.fromString(message));
    }

    public static void broadcast(boolean console, @NotNull Predicate<User> predicate, @NotNull Component component) {
        broadcast(console, predicate, user -> component);
    }

    public static void broadcast(boolean console, @NotNull Predicate<User> predicate, @NotNull Function<@Nullable User, Component> function) {
        for (User user : User.getUsers())
            if (predicate.test(user))
                message(user, function.apply(user));

        if (console)
            message(consoleCommandSource, function.apply(null));
    }

}
