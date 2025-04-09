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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.chat.ComponentUtil;
import xyz.dashnetwork.celest.chat.builder.sections.ComponentSection;
import xyz.dashnetwork.celest.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class PageBuilder {

    private final List<ComponentSection> sections = new ArrayList<>();

    public Section append(String text) {
        ComponentSection section = new ComponentSection(text);
        sections.add(section);

        return section;
    }

    public Component build(User user) { return build(user, 1); }

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
            return ComponentUtil.fromString("&6&l»&7 No entries found.");

        MessageBuilder builder = new MessageBuilder();
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