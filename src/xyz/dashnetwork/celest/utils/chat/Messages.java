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

import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.events.CelestChatEvent;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;

import java.util.function.Predicate;

public final class Messages {

    public static void chatMessage(NamedSource named, ChatType type, String message) {
        MessageBuilder builder = new MessageBuilder();
        Predicate<User> predicate;

        Celest.getServer().getEventManager().fireAndForget(new CelestChatEvent(named, type, message));

        switch (type) {
            case OWNER -> {
                builder.append("&9&lOwner&r ");
                builder.append(new NamedSourceFormat(named));
                builder.append("&r &c&l»&c");
                predicate = each -> each.isOwner() || each.getData().getChatType().equals(ChatType.OWNER);
            }
            case ADMIN -> {
                builder.append("&9&lAdmin&r ");
                builder.append(new NamedSourceFormat(named));
                builder.append("&r &3&l»&3");
                predicate = each -> each.isAdmin() || each.getData().getChatType().equals(ChatType.ADMIN);
            }
            case STAFF -> {
                builder.append("&9&lStaff&r ");
                builder.append(new NamedSourceFormat(named));
                builder.append("&r &6&l»&6");
                predicate = each -> each.isStaff() || each.getData().getChatType().equals(ChatType.STAFF);
            }
            case LOCAL -> {
                if (named instanceof User user) {
                    user.getPlayer().spoofChatInput(message);

                    if (message.startsWith("/")) {
                        builder = new MessageBuilder();
                        builder.append("&6&l»&r ");
                        builder.append(new NamedSourceFormat(named));
                        builder.append("&r &b&l»&b @lc" + message);

                        MessageUtils.broadcast(each -> each.getData().getCommandSpy(), builder::build);
                    }
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

}
