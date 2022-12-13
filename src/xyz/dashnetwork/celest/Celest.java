/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.Scheduler;
import org.slf4j.Logger;
import xyz.dashnetwork.celest.command.commands.*;
import xyz.dashnetwork.celest.inject.Injector;
import xyz.dashnetwork.celest.listeners.*;
import xyz.dashnetwork.celest.tasks.ClearTask;
import xyz.dashnetwork.celest.tasks.SaveTask;
import xyz.dashnetwork.celest.utils.ConfigurationList;
import xyz.dashnetwork.celest.utils.storage.Cache;
import xyz.dashnetwork.celest.utils.storage.Configuration;
import xyz.dashnetwork.celest.utils.storage.Storage;
import xyz.dashnetwork.celest.vault.Vault;
import xyz.dashnetwork.celest.vault.api.DummyAPI;
import xyz.dashnetwork.celest.vault.api.LuckPermsAPI;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Plugin(id = "celest", name = "Celest", version = "0.10", authors = {"MasterDash5"})
public final class Celest {

    private static Celest instance;
    private static ProxyServer server;
    private static Logger logger;
    private static Path directory;
    private static Vault vault;
    private static final ClearTask clearTask = new ClearTask();
    private static final SaveTask saveTask = new SaveTask();

    public static Celest getInstance() { return instance; }

    public static ProxyServer getServer() { return server; }

    public static Logger getLogger() { return logger; }

    public static Path getDirectory() { return directory; }

    public static Vault getVault() { return vault; }

    @Inject
    public Celest(ProxyServer server, Logger logger, @DataDirectory Path directory) {
        Celest.instance = this;
        Celest.server = server;
        Celest.logger = logger;
        Celest.directory = directory;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        logger.info("Starting...");
        long start = System.currentTimeMillis();

        Storage.mkdir();
        Configuration.load(); // TODO: Call this on a config reload command
        ConfigurationList.load(); // ^ and this
        Cache.load();

        if (server.getPluginManager().isLoaded("luckperms"))
            vault = new LuckPermsAPI();
        else {
            logger.warn("Couldn't find a permissions plugin for Vault, using fallback.");
            vault = new DummyAPI();
        }

        logger.info("Injecting channel initializer...");
        Injector.injectChannelInitializer(server);

        logger.info("Registering events...");
        EventManager eventManager = server.getEventManager();
        eventManager.register(this, new CommandExecuteListener());
        eventManager.register(this, new DisconnectListener());
        eventManager.register(this, new LoginListener());
        eventManager.register(this, new PlayerChatListener());
        eventManager.register(this, new PluginMessageListener());
        eventManager.register(this, new PostLoginListener());
        eventManager.register(this, new PreLoginListener());
        eventManager.register(this, new ProxyPingListener());
        eventManager.register(this, new ServerConnectedListener());
        eventManager.register(this, new ServerPostConnectListener());
        eventManager.register(this, new ServerPreConnectListener());

        logger.info("Registering channels...");


        logger.info("Registering commands...");
        new CommandBigMistakeBuddy();
        new CommandChat();
        new CommandClearChat();
        new CommandDiscord();
        new CommandMattsArmorStands();
        new CommandMommy();
        new CommandPing();
        new CommandTest();
        new CommandTheFurpySong();

        logger.info("Registering tasks...");
        Scheduler scheduler = server.getScheduler();
        scheduler.buildTask(this, clearTask).repeat(1, TimeUnit.HOURS).schedule();
        scheduler.buildTask(this, saveTask).repeat(1, TimeUnit.MINUTES).schedule();

        clearTask.run();
        logger.info("Startup complete. (took " + (System.currentTimeMillis() - start) + "ms)");
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) { saveTask.run(); }

}
