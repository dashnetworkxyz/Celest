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

package xyz.dashnetwork.celest;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.Scheduler;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.slf4j.Logger;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.channel.channels.input.ChannelInBroadcast;
import xyz.dashnetwork.celest.channel.channels.input.ChannelInOnline;
import xyz.dashnetwork.celest.channel.channels.input.ChannelInSignSpy;
import xyz.dashnetwork.celest.channel.channels.output.ChannelOutDisplayName;
import xyz.dashnetwork.celest.channel.channels.output.ChannelOutUserData;
import xyz.dashnetwork.celest.channel.channels.output.ChannelOutVanish;
import xyz.dashnetwork.celest.channel.channels.output.ChannelOutVersion;
import xyz.dashnetwork.celest.command.commands.*;
import xyz.dashnetwork.celest.listeners.*;
import xyz.dashnetwork.celest.tasks.CacheTask;
import xyz.dashnetwork.celest.tasks.SaveTask;
import xyz.dashnetwork.celest.utils.ConfigurationList;
import xyz.dashnetwork.celest.storage.Cache;
import xyz.dashnetwork.celest.storage.Configuration;
import xyz.dashnetwork.celest.storage.Storage;
import xyz.dashnetwork.celest.vault.Vault;
import xyz.dashnetwork.celest.vault.api.DummyAPI;
import xyz.dashnetwork.celest.vault.api.LuckPermsAPI;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Plugin(
        id = "celest",
        name = "Celest",
        version = "1.0.5",
        authors = {"MasterDash5"},
        dependencies = {
                @Dependency(id = "luckperms", optional = true),
                @Dependency(id = "signedvelocity", optional = true)
        }
)
public final class Celest {

    private static final Gson gson = new Gson();
    private static CacheTask cacheTask;
    private static SaveTask saveTask;
    private static AddressFactory addressFactory;
    private static UserFactory userFactory;
    private static Celest instance;
    private static ProxyServer server;
    private static Logger logger;
    private static Path directory;
    private static Vault vault;

    public static Gson getGson() { return gson; }

    public static CacheTask getCacheTask() { return cacheTask; }

    public static SaveTask getSaveTask() { return saveTask; }

    public static AddressFactory getAddressFactory() { return addressFactory; }

    public static UserFactory getUserFactory() { return userFactory; }

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

        // Init factories
        addressFactory = new AddressFactory();
        userFactory = new UserFactory();

        Storage.mkdir();
        Configuration.load();
        ConfigurationList.load();
        Cache.load();

        PluginManager pluginManager = server.getPluginManager();

        if (pluginManager.isLoaded("luckperms"))
            vault = new LuckPermsAPI();
        else {
            logger.warn("Couldn't find LuckPerms plugin for Vault, using fallback.");
            vault = new DummyAPI();
        }

        TagResolver.resolver("permission", (args, context) -> {
            String value = args.popOr("").value();
            Optional<UUID> uuid = context.target().get(Identity.UUID);

            return Tag.styling();
        });

        logger.info("Registering channels...");
        Channel.registerIn("broadcast", ChannelInBroadcast::new);
        Channel.registerIn("online", ChannelInOnline::new);
        Channel.registerIn("signspy", ChannelInSignSpy::new);
        Channel.registerOut("displayname", ChannelOutDisplayName::new);
        Channel.registerOut("userdata", ChannelOutUserData::new);
        Channel.registerOut("vanish", ChannelOutVanish::new);
        Channel.registerOut("version", ChannelOutVersion::new);

        logger.info("Registering listeners...");
        EventManager eventManager = server.getEventManager();
        eventManager.register(this, new CommandExecuteListener());
        eventManager.register(this, new DisconnectListener());
        eventManager.register(this, new GameProfileRequestListener());
        eventManager.register(this, new LoginListener());
        eventManager.register(this, new PluginMessageListener());
        eventManager.register(this, new PostLoginListener());
        eventManager.register(this, new PreLoginListener());
        eventManager.register(this, new ProxyPingListener());
        eventManager.register(this, new ServerConnectedListener());
        eventManager.register(this, new ServerPostConnectListener());
        eventManager.register(this, new ServerPreConnectListener());

        if (pluginManager.isLoaded("signedvelocity"))
            eventManager.register(this, new PlayerChatListener());
        else
            logger.warn("SignedVelocity is not loaded! Skipping PlayerChatListener...");

        logger.info("Registering commands...");
        new CommandAccounts();
        new CommandAdminChat();
        new CommandAltSpy();
        new CommandBan();
        new CommandBanList();
        new CommandBigMistakeBuddy();
        new CommandCelest();
        new CommandChat();
        new CommandClearChat();
        new CommandColorList();
        new CommandCommandSpy();
        new CommandCreative();
        new CommandDiscord();
        new CommandFakeJoin();
        new CommandFakeLeave();
        new CommandFakeOp();
        new CommandGlobalChat();
        new CommandGlobalList();
        new CommandHideAddress();
        new CommandIpBan();
        new CommandIpBanList();
        new CommandIpMute();
        new CommandKick();
        new CommandLobby();
        new CommandLocalChat();
        new CommandMattsArmorStands();
        new CommandMommy();
        new CommandMute();
        new CommandNickName();
        new CommandNostalgia();
        new CommandOwnerChat();
        new CommandPage();
        new CommandPing();
        new CommandPingSpy();
        new CommandProtocolList();
        new CommandPvp();
        new CommandRandom();
        new CommandRealJoin();
        new CommandRealName();
        new CommandSeen();
        new CommandServer();
        new CommandServerList();
        new CommandServerSpy();
        new CommandSignSpy();
        new CommandSkyblock();
        new CommandStaff();
        new CommandStaffChat();
        new CommandStafflegacy();
        new CommandSudo();
        new CommandSurvival();
        new CommandTalk();
        new CommandTempBan();
        new CommandTempIpBan();
        new CommandTempIpMute();
        new CommandTempMute();
        new CommandTheFurpySong();
        new CommandTwoFactor();
        new CommandUnban();
        new CommandUnIpBan();
        new CommandUnIpMute();
        new CommandUniqueId();
        new CommandUnmute();
        new CommandVanish();
        new CommandVersionList();

        logger.info("Scheduling tasks...");
        cacheTask = new CacheTask();
        saveTask = new SaveTask();

        Scheduler scheduler = server.getScheduler();
        scheduler.buildTask(this, cacheTask).repeat(1, TimeUnit.HOURS).schedule();
        scheduler.buildTask(this, saveTask).repeat(1, TimeUnit.MINUTES).schedule();

        cacheTask.run();
        logger.info("Startup complete. (took " + (System.currentTimeMillis() - start) + "ms)");
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) { saveTask.run(); }

}
