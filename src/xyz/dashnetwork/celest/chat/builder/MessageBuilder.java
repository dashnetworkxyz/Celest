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
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.dashnetwork.celest.chat.ColorUtil;
import xyz.dashnetwork.celest.chat.MessageUtil;
import xyz.dashnetwork.celest.chat.StyleUtil;
import xyz.dashnetwork.celest.chat.builder.sections.ComponentSection;
import xyz.dashnetwork.celest.chat.builder.sections.FormatSection;
import xyz.dashnetwork.celest.connection.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class MessageBuilder {

    private final List<ComponentSection> sections = new ArrayList<>();

    public int size() { return sections.size(); }

    public Section append(@NotNull String text) {
        ComponentSection section = new ComponentSection(text);
        sections.add(section);

        if (!StyleUtil.hasColorOrDecoration(section.getBuilder().build().style()))
            section.getBuilder().style(getLastStyle());

        return section;
    }

    public Section append(@NotNull Format format) {
        sections.addAll(format.sections());
        return new FormatSection(format);
    }

    public Section append(@NotNull ComponentSection section) {
        sections.add(section);
        return section;
    }

    public Component build(@Nullable User user) {
        TextComponent.Builder builder = Component.text();

        for (ComponentSection section : sections) {
            if (user == null || section.getUserPredicate().test(user)) {
                TextComponent.Builder subbuilder = section.getBuilder();
                List<Component> hovers = buildHoverForSection(user, section);

                if (!hovers.isEmpty()) {
                    Component hover = Component.join(JoinConfiguration.newlines(), hovers);
                    subbuilder.hoverEvent(HoverEvent.showText(hover));
                }

                builder.append(subbuilder);
            }
        }

        return builder.build();
    }

    public void message(@NotNull Audience audience) { MessageUtil.message(audience, this::build); }

    public void broadcast(@NotNull Predicate<User> filter) { MessageUtil.broadcast(filter, this::build); }

    public void broadcast() { MessageUtil.broadcast(this::build); }

    private Style getLastStyle() {
        if (sections.isEmpty())
            return Style.empty();

        return sections.get(sections.size() - 1).getBuilder().build().style();
    }

    private void addHoverComponent(User user, Section section, List<Component> hovers) {
        if (section instanceof FormatSection hoverFormat) {
            for (ComponentSection each : hoverFormat.getComponentSections())
                if (each.getUserPredicate().test(user))
                    hovers.add(each.getBuilder().build());

            return;
        }

        if (section instanceof ComponentSection hoverSection)
            if (hoverSection.getUserPredicate().test(user))
                hovers.add(hoverSection.getBuilder().build());
    }

    private List<Component> buildHoverForSection(User user, ComponentSection section) {
        List<Component> hovers = new ArrayList<>();

        for (Section hover : section.getHovers())
            if (user != null)
                addHoverComponent(user, hover, hovers);

        return hovers;
    }

}
