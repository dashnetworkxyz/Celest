/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.User;

import java.util.function.Predicate;

public class MessageUtils {

    private static final ProxyServer server = Celest.getServer();

    public static void message(Audience audience, String message) { message(audience, ColorUtils.toComponent(message)); }

    public static void message(Audience audience, Component component) { audience.sendMessage(component); }

    public static void broadcast(String message) { broadcast(ColorUtils.toComponent(message)); }

    public static void broadcast(Component component) { server.sendMessage(component); }

    public static void broadcast(Predicate<User> predicate, String message) { broadcast(predicate, ColorUtils.toComponent(message)); }

    public static void broadcast(Predicate<User> predicate, Component component) {
        for (User user : User.getUsers())
            if (predicate.test(user))
                user.getPlayer().sendMessage(component);

        server.getConsoleCommandSource().sendMessage(component);
    }

}
