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

package xyz.dashnetwork.celest.chat.builder.sections;

import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import xyz.dashnetwork.celest.chat.builder.Format;
import xyz.dashnetwork.celest.chat.builder.Section;
import xyz.dashnetwork.celest.connection.User;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class FormatSection implements Section {

    private final List<ComponentSection> sections;

    public FormatSection(Format format) { this.sections = format.sections(); }

    public List<ComponentSection> getComponentSections() { return sections; }

    private void forEach(Consumer<Section> consumer) {
        for (Section section : sections)
            consumer.accept(section);
    }

    @Override
    public FormatSection hover(String text) {
        forEach(each -> each.hover(text));
        return this;
    }

    @Override
    public FormatSection hover(String text, Consumer<Section> subsection) {
        forEach(each -> each.hover(text, subsection));
        return this;
    }

    @Override
    public FormatSection hover(char translate, String text) {
        forEach(each -> each.hover(translate, text));
        return this;
    }

    @Override
    public FormatSection hover(char translate, String text, Consumer<Section> subsection) {
        forEach(each -> each.hover(translate, text, subsection));
        return this;
    }

    @Override
    public FormatSection hover(Section section) {
        forEach(each -> each.hover(section));
        return this;
    }

    @Override
    public FormatSection click(ClickEvent event) {
        forEach(each -> each.click(event));
        return this;
    }

    @Override
    public FormatSection insertion(String insertion) {
        forEach(each -> each.insertion(insertion));
        return this;
    }

    @Override
    public FormatSection color(TextColor color) {
        forEach(each -> each.color(color));
        return this;
    }

    @Override
    public FormatSection colorIfAbsent(TextColor color) {
        forEach(each -> each.colorIfAbsent(color));
        return this;
    }

    @Override
    public FormatSection obfuscate() {
        forEach(Section::obfuscate);
        return this;
    }

    @Override
    public FormatSection obfuscate(boolean obfuscate) {
        forEach(each -> each.obfuscate(obfuscate));
        return this;
    }

    @Override
    public FormatSection bold() {
        forEach(Section::bold);
        return this;
    }

    @Override
    public FormatSection bold(boolean bold) {
        forEach(each -> each.bold(true));
        return this;
    }

    @Override
    public FormatSection strikethrough() {
        forEach(Section::strikethrough);
        return this;
    }

    @Override
    public FormatSection strikethrough(boolean strikethrough) {
        forEach(each -> each.strikethrough(strikethrough));
        return this;
    }

    @Override
    public FormatSection underline() {
        forEach(Section::underline);
        return this;
    }

    @Override
    public FormatSection underline(boolean underline) {
        forEach(each -> each.underline(underline));
        return this;
    }

    @Override
    public FormatSection italic() {
        forEach(Section::italic);
        return this;
    }

    @Override
    public FormatSection italic(boolean italic) {
        forEach(each -> each.italic(italic));
        return this;
    }

    @Override
    public FormatSection ifUser(Predicate<User> predicate) {
        forEach(each -> each.ifUser(predicate));
        return this;
    }

}
