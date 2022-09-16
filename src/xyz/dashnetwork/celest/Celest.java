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
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.Scheduler;
import org.slf4j.Logger;
import xyz.dashnetwork.celest.commands.CommandTest;
import xyz.dashnetwork.celest.listeners.*;
import xyz.dashnetwork.celest.storage.Cache;
import xyz.dashnetwork.celest.storage.Configuration;
import xyz.dashnetwork.celest.storage.Storage;
import xyz.dashnetwork.celest.tasks.ClearTask;
import xyz.dashnetwork.celest.tasks.SaveTask;
import xyz.dashnetwork.celest.utils.ConfigurationList;
import xyz.dashnetwork.celest.vault.Vault;
import xyz.dashnetwork.celest.vault.api.DummyAPI;
import xyz.dashnetwork.celest.vault.api.LuckAPI;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Plugin(id = "celest", name = "Celest", version = "1.0", authors = {"MasterDash5"})
public class Celest {

    private static ProxyServer server;
    private static Logger logger;
    private static Path directory;
    private static Vault vault;

    public static ProxyServer getServer() { return server; }

    public static Logger getLogger() { return logger; }

    public static Path getDirectory() { return directory; }

    public static Vault getVault() { return vault; }

    @Inject
    public Celest(ProxyServer server, Logger logger, @DataDirectory Path directory) {
        Celest.server = server;
        Celest.logger = logger;
        Celest.directory = directory;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        long start = System.currentTimeMillis();

        Storage.mkdir();
        Configuration.load();
        ConfigurationList.load(); // TODO: Call this on a config reload command
        Cache.load();
        Cache.removeOldEntries();

        for (Player player : server.getAllPlayers())
            new User(player); // Create User instances for plugin reloads.

        CommandManager commandManager = server.getCommandManager();
        commandManager.register("test", new CommandTest());

        EventManager eventManager = server.getEventManager();
        eventManager.register(this, new DisconnectListener());
        eventManager.register(this, new LoginListener());
        eventManager.register(this, new PlayerChatListener());
        eventManager.register(this, new PostLoginListener());
        eventManager.register(this, new ProxyPingListener());
        eventManager.register(this, new ServerPreConnectListener());

        Scheduler scheduler = server.getScheduler();
        scheduler.buildTask(this, new SaveTask()).repeat(1, TimeUnit.MINUTES);
        scheduler.buildTask(this, new ClearTask()).repeat(1, TimeUnit.DAYS);

        PluginManager pluginManager = server.getPluginManager();

        if (pluginManager.isLoaded("luckperms"))
            vault = new LuckAPI();
        else {
            logger.warn("Couldn't find a permissions plugin for Vault, using fallback.");
            vault = new DummyAPI();
        }

        logger.info("Startup complete. (took " + (System.currentTimeMillis() - start) + "ms)");
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        for (User user : User.getUsers())
            user.save();

        Cache.save();
    }

}
