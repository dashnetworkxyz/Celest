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

package xyz.dashnetwork.celest.chat.builder;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.chat.ComponentUtil;
import xyz.dashnetwork.celest.chat.MessageUtil;
import xyz.dashnetwork.celest.chat.builder.sections.ComponentSection;
import xyz.dashnetwork.celest.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class PageBuilder extends MessageBuilder {

    @Override
    public Component build(User user) { return build(user, 1); }

    public Component build(User user, int page) {
        if (user != null)
            user.setPageBuilder(this);

        int size = sections.size();
        int pages = (int) Math.ceil(size / 8d);
        int start = (page - 1) * 8;
        int end = Math.min(start + 8, size);

        if (start >= end)
            return ComponentUtil.fromString("&6&l»&7 No entries found.");

        MessageBuilder builder = new MessageBuilder();
        appendHeader(builder, page, pages, size, start, end);
        appendSections(builder, start, end);
        appendFooter(builder, page, pages);

        return builder.build(user);
    }

    public void message(@NotNull Audience audience) {
        MessageUtil.message(audience, this::build);
    }

    public void broadcast(@NotNull Predicate<User> predicate) {
        MessageUtil.broadcast(predicate, this::build);
    }

    public void broadcast() {
        MessageUtil.broadcast(this::build);
    }

    private void appendSections(MessageBuilder builder, int start, int end) {
        for (int i = start; i < end; i++) {
            builder.append("\n&7 - ");
            builder.append(sections.get(i));
        }
    }

    private void appendHeader(MessageBuilder builder, int page, int pages, int size, int start, int end) {
        builder.append("&6&l»&7 ---------- ");

        if (page > 1)
            builder.append("&6&l«&r ")
                    .hover("&7Click to go to &6page " + (page - 1))
                    .click(ClickEvent.runCommand("/page " + (page - 1)));

        builder.append("&6" + (start + 1) + "&7-&6" + end + "&7 / &6" + size + " ");

        if (page < pages)
            builder.append("&6&l»&r ")
                    .hover("&7Click to go to &6page " + (page + 1))
                    .click(ClickEvent.runCommand("/page " + (page + 1)));

        builder.append("&7----------");
    }

    private void appendFooter(MessageBuilder builder, int page, int pages) {
        builder.append("\n&6&l»&7 ---------- ");

        if (page > 1)
            builder.append("&6&l«&r ")
                    .hover("&7Click to go to &6page " + (page - 1))
                    .click(ClickEvent.runCommand("/page " + (page - 1)));

        builder.append("&7Page &6" + page + "&7 / &6" + pages + " ");

        if (page < pages)
            builder.append("&6&l»&r ")
                    .hover("&7Click to go to &6page " + (page + 1))
                    .click(ClickEvent.runCommand("/page " + (page + 1)));

        builder.append("&7----------");
    }

}