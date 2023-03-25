/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils.chat;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.events.CelestChatEvent;
import xyz.dashnetwork.celest.utils.LazyUtils;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Messages {

    public static void chatMessage(NamedSource named, Channel channel, String message) {
        MessageBuilder builder = new MessageBuilder();
        Predicate<User> predicate;

        Celest.getServer().getEventManager().fireAndForget(new CelestChatEvent(named, channel, message));

        switch (channel) {
            case OWNER -> {
                builder.append("&9&lOwner&r ");
                builder.append(new NamedSourceFormat(named));
                builder.append("&r &c&l»&c");
                predicate = each -> each.isOwner() || each.getData().getChannel().equals(Channel.OWNER);
            }
            case ADMIN -> {
                builder.append("&9&lAdmin&r ");
                builder.append(new NamedSourceFormat(named));
                builder.append("&r &3&l»&3");
                predicate = each -> each.isAdmin() || each.getData().getChannel().equals(Channel.ADMIN);
            }
            case STAFF -> {
                builder.append("&9&lStaff&r ");
                builder.append(new NamedSourceFormat(named));
                builder.append("&r &6&l»&6");
                predicate = each -> each.isStaff() || each.getData().getChannel().equals(Channel.STAFF);
            }
            case LOCAL -> {
                if (named instanceof User user) {
                    Player player = user.getPlayer();

                    if (message.startsWith("/")) {
                        if (LazyUtils.anyEquals(player.getProtocolVersion(),
                                ProtocolVersion.MINECRAFT_1_19, ProtocolVersion.MINECRAFT_1_19_1)) {
                            MessageUtils.message(player, "&6&l»&7 Using commands in &6@lc&7 is disabled in &61.19-1.19.2");
                            return;
                        }

                        builder = new MessageBuilder();
                        builder.append("&6&l»&r ");
                        builder.append(new NamedSourceFormat(named));
                        builder.append("&r &b&l»&b @lc" + message);

                        MessageUtils.broadcast(each -> each.getData().getCommandSpy(), builder::build);
                    }

                    player.spoofChatInput(message);
                }

                return;
            }
            default -> {
                builder.append(new NamedSourceFormat(named));
                builder.append("&r &e&l»&r");
                predicate = each -> true;
            }
        }

        for (String split : message.split(" ")) {
            if (split.length() > 0) {
                TextSection section = builder.append(" " + split);

                if (StringUtils.matchesUrl(split)) {
                    String url = split.toLowerCase().startsWith("http") ? split : "https://" + split;

                    section.hover("&7Click to open &6" + url).click(ClickEvent.openUrl(url));
                }
            }
        }

        MessageUtils.broadcast(predicate, builder::build);
    }

    public static void serverlistMessage(CommandSource source) {
        Optional<User> optional = User.getUser(source);
        MessageBuilder builder = new MessageBuilder();

        builder.append("&6&l»&7 Available servers: ");

        for (RegisteredServer server : Celest.getServer().getAllServers()) {
            String name = server.getServerInfo().getName();
            Function<User, Boolean> permission =
                    user -> user.isOwner() || user.getPlayer().hasPermission("dashnetwork.server." + name);

            if (optional.map(permission).orElse(true)) {
                if (builder.length() > 1)
                    builder.append("&7, ");

                builder.append("&6" + name)
                        .hover("&7Click to copy &6/server " + name)
                        .click(ClickEvent.suggestCommand("/server " + name));
            }
        }

        if (builder.length() == 1)
            builder.append("&cNo servers found");

        MessageUtils.message(source, builder::build);
    }

}
