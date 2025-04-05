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

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.ArrayUtils;
import xyz.dashnetwork.celest.utils.ConfigurationList;
import xyz.dashnetwork.celest.utils.GithubUtils;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.chat.MessageUtils;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.PageBuilder;
import xyz.dashnetwork.celest.connection.Address;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.limbo.Limbo;
import xyz.dashnetwork.celest.log.Logger;
import xyz.dashnetwork.celest.profile.OfflineUser;
import xyz.dashnetwork.celest.storage.Configuration;
import xyz.dashnetwork.celest.storage.LegacyParser;
import xyz.dashnetwork.celest.storage.Storage;
import xyz.dashnetwork.celest.storage.data.AddressData;
import xyz.dashnetwork.celest.storage.data.UserData;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class CommandCelest extends CelestCommand {

    public CommandCelest() {
        super("celest", "cel");

        setPermission(User::isOwner, true);
        addArguments(false, ArgumentType.STRING);
        setSuggestions(0, "reload", "save", "version", "debug", "users", "cache", "flush", "data");
    }

    private void sendHelpMessage(CommandSource source) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&6 Celest debug commands");
        builder.append("\n&6&l»&b /celest legacy-import &c&o(unsafe)").hover("&6Import legacy data &c(unsafe).");
        builder.append("\n&6&l»&b /celest flush").hover("&6Clear & save all objects in Limbo.");
        builder.append("\n&6&l»&b /celest cache").hover("&6Refresh and clear old cache.");
        builder.append("\n&6&l»&b /celest user").hover("&6Get and view all users from userdata.");
        builder.append("\n&6&l»&b /celest address").hover("&6Get and view all users from userdata.");
        builder.append("\n&6&l»&b /celest debug").hover("&6View debug information.");
        builder.append("\n&6&l»&b /celest version").hover("&6View build properties.");
        builder.append("\n&6&l»&b /celest save").hover("&6Force an auto-save.");
        builder.append("\n&6&l»&b /celest reload").hover("&6Reload config.yml.");
        builder.message(source);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<String> optionalCommand = arguments.optional(String.class);

        if (optionalCommand.isEmpty()) {
            sendHelpMessage(source);
            return;
        }

        switch (optionalCommand.get().toLowerCase()) {
            case "address", "a" -> {
                MessageUtils.message(source, "&6&l»&7 Reading addressdata...");

                List<Limbo<Address>> limbos = Limbo.getAll(Address.class, each -> true);
                Map<String, AddressData> map = Storage.readAll(Storage.Directory.ADDRESS, AddressData.class);
                PageBuilder builder = new PageBuilder();

                for (Limbo<Address> limbo : limbos) {
                    Address address = limbo.getObject();

                    map.put(address.getString(), address.getData());
                }

                for (Map.Entry<String, AddressData> entry : map.entrySet()) {
                    String address = entry.getKey();
                    AddressData data = entry.getValue();

                    builder.append("&6" + address)
                            .hover("&6" + address
                                    + "\n&7ban: &6" + data.getBan()
                                    + "\n&7mute: &6" + data.getMute()
                                    + "\n&7Profiles: &6" + ArrayUtils.convertToString(
                                            data.getProfiles(),
                                            profile -> profile.username() + " " + profile.uuid(),
                                            "&7, &6"
                                    )
                            ).insertion(address);
                }

                MessageUtils.message(source, builder::build);
            }
            case "reload", "r" -> {
                Configuration.load();
                ConfigurationList.load();
                MessageUtils.message(source, "&6&l»&7 config.yml reloaded.");
            }
            case "save", "s" -> {
                Celest.getSaveTask().run();
                MessageUtils.message(source, "&6&l»&7 Save complete.");
            }
            case "version", "ver", "v" -> {
                URL resource = CommandCelest.class.getClassLoader().getResource("build.properties");
                assert resource != null;

                String[] properties;

                try {
                    InputStream stream = resource.openStream();
                    properties = new String(stream.readAllBytes()).split("\r\n");

                    stream.close();
                } catch (IOException exception) {
                    MessageUtils.message(source, "&6&l»&7 Failed to get build properties.");
                    Logger.throwable(exception);
                    return;
                }

                if (properties.length < 3 || properties[3].equals("${describe}")) {
                    MessageUtils.message(source, "&6&l»&7 No build information is available.");
                    return;
                }

                String date = TimeUtils.longToDate(Long.parseLong(properties[4]) * 1000);

                MessageBuilder builder = new MessageBuilder();
                builder.append("&6&l»&7 " + properties[0] + " &6" + properties[1] + " " + properties[2]);
                builder.append("\n&6&l»&7 ");
                builder.append("&6" + properties[3])
                        .hover("&7Click to copy &6" + properties[3])
                        .click(ClickEvent.suggestCommand(properties[3]));
                builder.append("&7 ");
                builder.append("&7(" + date + ")")
                        .hover("&7Click to copy &6" + properties[4])
                        .click(ClickEvent.suggestCommand(properties[4]));
                builder.append("\n&6&l»&7 Checking for updates...");
                builder.message(source);

                String hash = properties[3].split("-")[2];
                int distance = GithubUtils.getGitDistance("dashnetworkxyz/Celest", "master", hash);

                switch (distance) {
                    case -1 -> MessageUtils.message(source, "&6&l»&7 Unable to fetch version from Github.");
                    case 0 -> MessageUtils.message(source, "&6&l»&7 You are using the &6latest version&7.");
                    default -> MessageUtils.message(source, "&6&l»&7 You are &6" + distance + " versions&7 behind.");
                }
            }
            case "debug" -> {
                boolean state;

                if (source instanceof Player player) {
                    UserData data = User.getUser(player).getData();
                    state = !data.getDebug();

                    data.setDebug(state);
                } else {
                    state = !ConfigurationList.DEBUG;
                    ConfigurationList.DEBUG = state;
                }

                if (state)
                    MessageUtils.message(source, "&6&l»&7 You are now in &6Debug&7.");
                else
                    MessageUtils.message(source, "&6&l»&7 You are no longer in &6Debug&7.");
            }
            case "user", "u" -> {
                MessageUtils.message(source, "&6&l»&7 Reading userdata...");

                List<Limbo<OfflineUser>> limbos = Limbo.getAll(OfflineUser.class, each -> true);
                Map<String, UserData> map = Storage.readAll(Storage.Directory.USER, UserData.class);
                PageBuilder builder = new PageBuilder();

                for (Limbo<OfflineUser> limbo : limbos) {
                    OfflineUser offline = limbo.getObject();

                    map.put(offline.getUuid().toString(), offline.getData());
                }

                for (Map.Entry<String, UserData> entry : map.entrySet()) {
                    String uuid = entry.getKey();
                    UserData data = entry.getValue();
                    String username = data.getUsername();

                    builder.append("&6" + username)
                            .hover("&6" + uuid
                                    + "\n&7address: &6" + data.getAddress()
                                    + "\n&7username: &6" + username
                                    + "\n&7nickname: &6" + data.getNickName()
                                    + "\n&7ban: &6" + data.getBan()
                                    + "\n&7mute: &6" + data.getMute()
                                    + "\n&7channel: &6" + data.getChannel()
                                    + "\n&7authenticated: &6" + data.getAuthenticated()
                                    + "\n&7altSpy: &6" + data.getAltSpy()
                                    + "\n&7commandSpy: &6" + data.getCommandSpy()
                                    + "\n&7pingSpy: &6" + data.getPingSpy()
                                    + "\n&7signSpy: &6" + data.getSignSpy()
                                    + "\n&7serverSpy: &6" + data.getServerSpy()
                                    + "\n&7vanish: &6" + data.getVanish()
                                    + "\n&7hideAddress: &6" + data.getHideAddress()
                                    + "\n&7debug: &6" + data.getDebug()
                            ).insertion(uuid);
                }

                MessageUtils.message(source, builder::build);
            }
            case "cache", "c" -> {
                Celest.getCacheTask().run();
                MessageUtils.message(source, "&6&l»&7 Cache refreshed and old entries removed.");
            }
            case "flush", "f" -> {
                for (Limbo<?> limbo : Limbo.getLimbos()) {
                    limbo.cancel();
                    limbo.save();
                }

                MessageUtils.message(source, "&6&l»&7 All objects in limbo cleared & saved.");
            }
            case "legacy-import" -> {
                MessageUtils.message(source, "&6&l»&7 Reading legacy data...");

                LegacyParser parser = new LegacyParser();
                parser.read();

                MessageUtils.message(source, "&6&l»&7 Writing legacy data...");
                parser.write();

                MessageUtils.message(source, "&6&l»&7 Completed legacy import.");
            }
            default -> sendHelpMessage(source);
        }
    }

}
