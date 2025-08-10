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

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.events.CelestChatEvent;
import xyz.dashnetwork.celest.utils.GrammarUtils;
import xyz.dashnetwork.celest.utils.LazyUtils;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.Section;
import xyz.dashnetwork.celest.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.profile.NamedSource;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Messages {

    public static void chatMessage(NamedSource named, ChatChannel channel, String message) {
        MessageBuilder builder = new MessageBuilder();
        Predicate<User> predicate;

        switch (channel) {
            case OWNER -> {
                builder.append("&9&lOwner&f ");
                builder.append(new NamedSourceFormat(named));
                builder.append("&f &c&l»&c");
                predicate = each -> each.isOwner() || each.getData().getChannel().equals(ChatChannel.OWNER);
            }
            case ADMIN -> {
                builder.append("&9&lAdmin&f ");
                builder.append(new NamedSourceFormat(named));
                builder.append("&f &3&l»&3");
                predicate = each -> each.isAdmin() || each.getData().getChannel().equals(ChatChannel.ADMIN);
            }
            case STAFF -> {
                builder.append("&9&lStaff&f ");
                builder.append(new NamedSourceFormat(named));
                builder.append("&f &6&l»&6");
                predicate = each -> each.isStaff() || each.getData().getChannel().equals(ChatChannel.STAFF);
            }
            case LOCAL -> {
                if (named instanceof User user) {
                    Player player = user.getPlayer();

                    if (message.startsWith("/")) {
                        if (LazyUtils.anyEquals(player.getProtocolVersion(),
                                ProtocolVersion.MINECRAFT_1_19, ProtocolVersion.MINECRAFT_1_19_1)) {
                            MessageUtils.message(player,
                                    "&6&l»&7 Using commands in &6@lc&7 is disabled in &61.19-1.19.2&7.");
                            return;
                        }

                        builder = new MessageBuilder();
                        builder.append("&b&l»&f ");
                        builder.append(new NamedSourceFormat(named));
                        builder.append("&b @lc" + message);
                        builder.broadcast(each -> each.getData().getCommandSpy());
                    }

                    player.spoofChatInput(message);
                }

                return;
            }
            default -> {
                builder.append(new NamedSourceFormat(named));
                builder.append("&f &l»&f");
                predicate = each -> true;
            }
        }

        for (String split : message.split(" ")) {
            if (!split.isEmpty()) {
                builder.append(" ");
                Section section = builder.append(split);

                if (StringUtils.matchesUrl(split)) {
                    String url = split.toLowerCase().startsWith("http") ? split : "https://" + split;

                    section.hover("&7Click to open &6" + url).click(ClickEvent.openUrl(url));
                }
            }
        }

        builder.broadcast(predicate);
        Celest.getServer().getEventManager().fireAndForget(new CelestChatEvent(named, channel, message));
    }

    public static void serverlistMessage(CommandSource source) {
        Optional<User> optional = User.getUser(source);
        MessageBuilder builder = new MessageBuilder();

        builder.append("&6&l»&7 Available servers: ");

        for (RegisteredServer server : Celest.getServer().getAllServers()) {
            String name = GrammarUtils.capitalization(server.getServerInfo().getName());
            Function<User, Boolean> permission =
                    user -> user.isOwner() || user.getPlayer().hasPermission("dashnetwork.server." + name.toLowerCase());

            if (optional.map(permission).orElse(true)) {
                if (builder.size() > 1)
                    builder.append("&7, ");

                builder.append("&6" + name)
                        .hover("&7Click to copy &6/server " + name)
                        .click(ClickEvent.suggestCommand("/server " + name));
            }
        }

        if (builder.size() == 1)
            builder.append("&cNo servers found");

        builder.message(source);
    }

}
