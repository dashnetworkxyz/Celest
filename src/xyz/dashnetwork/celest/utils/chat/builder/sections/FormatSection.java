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

package xyz.dashnetwork.celest.utils.chat.builder.sections;

import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.Section;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.List;
import java.util.function.Predicate;

public final class FormatSection implements Section {

    private final List<ComponentSection> sections;

    public FormatSection(Format format) { this.sections = format.sections(); }

    @Override
    public Section hover(String text) {
        for (Section section : sections)
            section.hover(text);
        return this;
    }

    @Override
    public Section hover(String text, Predicate<User> filter) {
        for (Section section : sections)
            section.hover(text, filter);
        return this;
    }

    @Override
    public Section click(ClickEvent event) {
        for (Section section : sections)
            section.click(event);
        return this;
    }

    @Override
    public Section insertion(String insertion) {
        for (Section section : sections)
            section.insertion(insertion);
        return this;
    }

    @Override
    public Section color(TextColor color) {
        for (Section section : sections)
            section.color(color);
        return this;
    }

    @Override
    public Section filter(Predicate<User> filter) {
        for (Section section : sections)
            section.filter(filter);
        return this;
    }

}
