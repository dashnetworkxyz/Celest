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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import xyz.dashnetwork.celest.chat.builder.Section;
import xyz.dashnetwork.celest.connection.User;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ComponentSection implements Section {

    private final List<Section> hovers = new LinkedList<>();
    private final TextComponent.Builder builder = Component.text();
    private Predicate<User> userPredicate = user -> true;

    public ComponentSection(String text) {
        builder.append(Component.text(text));
    }

    public ComponentSection(char translate, String text) {
        builder.append(LegacyComponentSerializer.legacy(translate).deserialize(text));
    }

    public TextComponent.Builder getBuilder() { return builder; }

    public List<Section> getHovers() { return hovers; }

    public Predicate<User> getUserPredicate() { return userPredicate; }

    @Override
    public ComponentSection hover(String text) {
        hovers.add(new ComponentSection(text));
        return this;
    }

    @Override
    public ComponentSection hover(String text, Consumer<Section> subsection) {
        Section section = new ComponentSection(text);
        hovers.add(section);
        subsection.accept(section);
        return this;
    }

    @Override
    public ComponentSection hover(char translate, String text) {
        hovers.add(new ComponentSection(translate, text));
        return this;
    }

    @Override
    public ComponentSection hover(char translate, String text, Consumer<Section> subsection) {
        Section section = new ComponentSection(translate, text);
        hovers.add(section);
        subsection.accept(section);
        return this;
    }

    @Override
    public ComponentSection hover(Section section) {
        hovers.add(section);
        return this;
    }

    @Override
    public ComponentSection click(ClickEvent event) {
        builder.clickEvent(event);
        return this;
    }

    @Override
    public ComponentSection insertion(String insertion) {
        builder.insertion(insertion);
        return this;
    }

    @Override
    public ComponentSection color(TextColor color) {
        builder.color(color);
        return this;
    }

    @Override
    public ComponentSection colorIfAbsent(TextColor color) {
        builder.colorIfAbsent(color);
        return this;
    }

    @Override
    public ComponentSection obfuscate() {
        return obfuscate(true);
    }

    @Override
    public ComponentSection obfuscate(boolean obfuscate) {
        builder.decoration(TextDecoration.OBFUSCATED, obfuscate);
        return this;
    }

    @Override
    public ComponentSection bold() {
        return bold(true);
    }

    @Override
    public ComponentSection bold(boolean bold) {
        builder.decoration(TextDecoration.BOLD, bold);
        return this;
    }

    @Override
    public ComponentSection strikethrough() {
        return strikethrough(true);
    }

    @Override
    public ComponentSection strikethrough(boolean strikethrough) {
        builder.decoration(TextDecoration.STRIKETHROUGH, strikethrough);
        return this;
    }

    @Override
    public ComponentSection underline() {
        return underline(true);
    }

    @Override
    public ComponentSection underline(boolean underline) {
        builder.decoration(TextDecoration.UNDERLINED, underline);
        return this;
    }

    @Override
    public ComponentSection italic() {
        return italic(true);
    }

    @Override
    public ComponentSection italic(boolean italic) {
        builder.decoration(TextDecoration.ITALIC, italic);
        return this;
    }

    @Override
    public ComponentSection ifUser(Predicate<User> predicate) {
        this.userPredicate = predicate;
        return this;
    }

}