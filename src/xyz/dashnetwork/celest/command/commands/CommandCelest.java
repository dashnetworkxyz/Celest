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

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.ConfigurationList;
import xyz.dashnetwork.celest.utils.GithubUtils;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.connection.OfflineUser;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.limbo.Limbo;
import xyz.dashnetwork.celest.utils.log.Logger;
import xyz.dashnetwork.celest.utils.storage.Configuration;
import xyz.dashnetwork.celest.utils.storage.LegacyParser;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public final class CommandCelest extends CelestCommand {

    public CommandCelest() {
        super("celest");

        setPermission(User::isOwner, true);
        addArguments(ArgumentType.STRING);
        addArguments(ArgumentType.STRING);
        setCompletions(0, "reload", "save", "version", "debug", "flush");
    }

    private void sendHelpMessage(CommandSource source) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&6 Celest debug commands");
        builder.append("\n&6&l»&7 /celest legacy-import &c&o(unsafe)").hover("&6Import legacy data &c(unsafe)");
        builder.append("\n&6&l»&7 /celest data <user/address/lookup> // TODO"); // TODO
        builder.append("\n&6&l»&7 /celest flush").hover("&6Clear & save all objects in Limbo.");
        builder.append("\n&6&l»&7 /celest debug").hover("&6View debug information");
        builder.append("\n&6&l»&7 /celest version").hover("&6View build properties");
        builder.append("\n&6&l»&7 /celest save").hover("&6Force an auto-save");
        builder.append("\n&6&l»&7 /celest reload").hover("&6Reload config.yml");

        MessageUtils.message(source, builder::build);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<String> optionalCommand = arguments.get(String.class);

        if (optionalCommand.isEmpty()) {
            sendHelpMessage(source);
            return;
        }

        switch (optionalCommand.get().toLowerCase()) {
            case "reload" -> {
                Configuration.load();
                ConfigurationList.load();
                MessageUtils.message(source, "&6&l»&7 config.yml reloaded.");
            }
            case "save" -> {
                Celest.getSaveTask().run();
                MessageUtils.message(source, "&6&l»&7 Save complete.");
            }
            case "version", "ver" -> {
                URL resource = CommandCelest.class.getClassLoader().getResource("build.properties");
                assert resource != null;

                String[] properties;

                try {
                    properties = new String(resource.openStream().readAllBytes()).split("\r\n");
                } catch (IOException exception) {
                    MessageUtils.message(source, "&6&l»&7 Failed to get build properties");
                    Logger.throwable(exception);
                    return;
                }

                if (properties[3].equals("${describe}")) {
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

                MessageUtils.message(source, builder::build);

                String hash = properties[3].split("-")[2];
                int distance = GithubUtils.getGitDistance("dashnetworkxyz/Celest", "master", hash);

                switch (distance) {
                    case -1 -> MessageUtils.message(source, "&6&l»&7 Unable to fetch version from Github");
                    case 0 -> MessageUtils.message(source, "&6&l»&7 You are using the &6latest version");
                    default -> MessageUtils.message(source, "&6&l»&7 You are &6" + distance + " versions&7 behind");
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
                    MessageUtils.message(source, "&6&l»&7 You are now in &6Debug");
                else
                    MessageUtils.message(source, "&6&l»&7 You are no longer in &6Debug");
            }
            case "flush" -> {
                for (Limbo<?> limbo : Limbo.getLimbos()) {
                    limbo.cancel();
                    limbo.save();
                }

                MessageUtils.message(source, "&6&l»&7 All objects in limbo cleared & saved.");
            }
            case "data" -> {
                MessageUtils.message(source, "//TODO"); // TODO
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
