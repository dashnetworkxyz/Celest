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
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import xyz.dashnetwork.celest.chat.ColorUtil;
import xyz.dashnetwork.celest.chat.ComponentUtil;
import xyz.dashnetwork.celest.chat.StyleUtil;
import xyz.dashnetwork.celest.chat.builder.Section;
import xyz.dashnetwork.celest.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class ComponentSection implements Section {

    private static final LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();
    private final TextComponent.Builder builder = Component.text();
    private final List<ComponentSection> hovers = new ArrayList<>();
    private Predicate<User> filter = user -> true;

    public ComponentSection(String text) {
        String colored = ColorUtil.fromAmpersand(text).replace("§r", "§f");
        builder.append(serializer.deserialize(colored));
    }

    public TextComponent.Builder getBuilder() { return builder; }

    public List<ComponentSection> getHovers() { return hovers; }

    public Predicate<User> getFilter() { return filter; }

    public Style getLastStyle() {
        Component component = builder.build();
        List<Component> children = ComponentUtil.getAllChildren(component);
        Style style = null;

        for (int i = children.size() - 1; i >= 0; i--) {
            Style child = children.get(i).style();

            if (StyleUtil.hasColor(child)) {
                style = child;
                break;
            }
        }

        if (style == null)
            style = component.style();

        return style;
    }

    @Override
    public Section hover(String text) {
        hovers.add(new ComponentSection(text));
        return this;
    }

    @Override
    public Section hover(String text, Predicate<User> filter) {
        hovers.add((ComponentSection) new ComponentSection(text).filter(filter));
        return this;
    }

    @Override
    public Section click(ClickEvent event) {
        builder.clickEvent(event);
        return this;
    }

    @Override
    public Section insertion(String insertion) {
        builder.insertion(insertion);
        return this;
    }

    @Override
    public Section color(TextColor color) {
        builder.colorIfAbsent(color);
        return this;
    }

    @Override
    public Section filter(Predicate<User> filter) {
        this.filter = filter;
        return this;
    }

}