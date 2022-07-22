/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.Scheduler;
import org.slf4j.Logger;
import xyz.dashnetwork.celest.commands.CommandTest;
import xyz.dashnetwork.celest.listeners.DisconnectListener;
import xyz.dashnetwork.celest.listeners.LoginListener;
import xyz.dashnetwork.celest.listeners.ServerConnectListener;
import xyz.dashnetwork.celest.tasks.SaveTask;
import xyz.dashnetwork.celest.utils.Cache;
import xyz.dashnetwork.celest.utils.Storage;

import java.util.concurrent.TimeUnit;

@Plugin(id = "celest", name = "Celect", version = "1.0", authors = {"MasterDash5"})
public class Celest {

    private static ProxyServer server;
    private static Logger logger;

    public static ProxyServer getServer() { return server; }

    public static Logger getLogger() { return logger; }

    @Inject
    public Celest(ProxyServer server, Logger logger) {
        Celest.server = server;
        Celest.logger = logger;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        for (Player player : server.getAllPlayers())
            new User(player); // Create User instances for plugin reloads.

        Storage.mkdir();
        Cache.load();

        CommandManager commandManager = server.getCommandManager();
        commandManager.register("test", new CommandTest());

        EventManager eventManager = server.getEventManager();
        eventManager.register(this, new DisconnectListener());
        eventManager.register(this, new LoginListener());
        eventManager.register(this, new ServerConnectListener());

        Scheduler scheduler = server.getScheduler();
        scheduler.buildTask(this, new SaveTask()).repeat(1, TimeUnit.MINUTES);
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        for (User user : User.getUsers())
            user.save();

        Cache.save();
    }

}
