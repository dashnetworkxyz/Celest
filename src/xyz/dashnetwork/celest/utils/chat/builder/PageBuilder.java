/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils.chat.builder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class PageBuilder {

    private final List<TextSection> sections = new ArrayList<>();

    public TextSection append(String text) {
        TextSection section = new TextSection(text, null, null);
        sections.add(section);

        return section;
    }

    // TODO: Cleanup
    public Component build(User user, int page) {
        if (user != null)
            user.setPageBuilder(this);

        int size = sections.size();
        int pages = (int) Math.ceil(size / 8d);
        int start = (page - 1) * 8;
        int end = start + 8;

        if (end > size)
            end = size;

        if (start >= end)
            return ComponentUtils.fromString("&6&l»&7 No entries found.");

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 ---------- ");

        if (page > 1)
            builder.append("&6&l«&r ")
                    .hover("&7Click to go to &6page " + (page - 1))
                    .click(ClickEvent.runCommand("/page " + (page - 1)));

        builder.append("&6" + (start + 1) + "-" + end + "&7 / &6" + size + " ");

        if (page < pages)
            builder.append("&6&l»&r ")
                    .hover("&7Click to go to &6page " + (page + 1))
                    .click(ClickEvent.runCommand("/page " + (page + 1)));

        builder.append("&7----------");

        for (int i = start; i < end; i++) {
            builder.append("\n&7 - ");
            builder.append(sections.get(i));
        }

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

        return builder.build(user);
    }

}
