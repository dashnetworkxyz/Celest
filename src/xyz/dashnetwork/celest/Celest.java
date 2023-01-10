/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest;

import com.google.gson.Gson;
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
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.channel.channels.input.ChannelBroadcast;
import xyz.dashnetwork.celest.channel.channels.input.ChannelOnline;
import xyz.dashnetwork.celest.channel.channels.input.ChannelSignSpy;
import xyz.dashnetwork.celest.channel.channels.output.ChannelDisplayName;
import xyz.dashnetwork.celest.channel.channels.output.ChannelTwoFactor;
import xyz.dashnetwork.celest.channel.channels.output.ChannelVanish;
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

@Plugin(id = "celest", name = "Celest", version = "0.12", authors = {"MasterDash5"})
public final class Celest {

    private static final Gson gson = new Gson();
    private static final ClearTask clearTask = new ClearTask();
    private static final SaveTask saveTask = new SaveTask();
    private static Celest instance;
    private static ProxyServer server;
    private static Logger logger;
    private static Path directory;
    private static Vault vault;

    public static Gson getGson() { return gson; }

    public static SaveTask getSaveTask() { return saveTask; }

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
        Configuration.load();
        ConfigurationList.load();
        Cache.load();

        if (server.getPluginManager().isLoaded("luckperms"))
            vault = new LuckPermsAPI();
        else {
            logger.warn("Couldn't find a permissions plugin for Vault, using fallback.");
            vault = new DummyAPI();
        }

        logger.info("Injecting channel initializer...");
        Injector.injectChannelInitializer(server);

        logger.info("Registering channels...");
        Channel.registerIn("broadcast", ChannelBroadcast::new);
        Channel.registerIn("online", ChannelOnline::new);
        Channel.registerIn("signspy", ChannelSignSpy::new);
        Channel.registerOut("displayname", ChannelDisplayName::new);
        Channel.registerOut("twofactor", ChannelTwoFactor::new);
        Channel.registerOut("vanish", ChannelVanish::new);

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

        logger.info("Registering commands...");
        new CommandAccounts();
        new CommandAdminChat();
        new CommandAltSpy();
        new CommandBan();
        new CommandBigMistakeBuddy();
        new CommandCelest();
        new CommandChat();
        new CommandClearChat();
        new CommandColorList();
        new CommandCommandSpy();
        new CommandDiscord();
        new CommandFakeJoin();
        new CommandFakeLeave();
        new CommandFakeOp();
        new CommandGlobalChat();
        new CommandGlobalList();
        new CommandHideAddress();
        new CommandIpBan();
        new CommandKick();
        new CommandLocalChat();
        new CommandMattsArmorStands();
        new CommandMommy();
        new CommandMute();
        new CommandNickName();
        new CommandOwnerChat();
        new CommandPing();
        new CommandPingSpy();
        new CommandRealName();
        new CommandSeen();
        new CommandServer();
        new CommandStaffChat();
        new CommandTempBan();
        new CommandTempMute();
        new CommandTest();
        new CommandTheFurpySong();
        new CommandTwoFactor();
        new CommandUnban();
        new CommandUniqueId();
        new CommandUnmute();
        new CommandVanish();
        new CommandVersionList();

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
