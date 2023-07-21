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
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.Section;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class FormatSection implements Section {

    private final List<ComponentSection> sections;

    public FormatSection(Format format) { this.sections = format.sections(); }

    private void forEach(Consumer<Section> consumer) {
        for (Section section : sections)
            consumer.accept(section);
    }

    @Override
    public Section hover(String text) {
        forEach(section -> section.hover(text));
        return this;
    }

    @Override
    public Section hover(String text, Predicate<User> filter) {
        forEach(section -> section.hover(text, filter));
        return this;
    }

    @Override
    public Section click(ClickEvent event) {
        forEach(section -> section.click(event));
        return this;
    }

    @Override
    public Section insertion(String insertion) {
        forEach(section -> section.insertion(insertion));
        return this;
    }

    @Override
    public Section filter(Predicate<User> filter) {
        forEach(section -> section.filter(filter));
        return this;
    }

}
