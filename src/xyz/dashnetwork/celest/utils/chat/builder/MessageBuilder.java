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

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.dashnetwork.celest.utils.chat.ColorUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.sections.ComponentSection;
import xyz.dashnetwork.celest.utils.chat.builder.sections.FormatSection;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class MessageBuilder {

    private final List<ComponentSection> sections = new ArrayList<>();

    public int size() { return sections.size(); }

    public Section append(@NotNull String text) {
        ComponentSection section = new ComponentSection(getStyleFromPrevious() + text);
        sections.add(section);

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
            if (user == null || section.getFilter().test(user)) {
                List<Component> hovers = new ArrayList<>();
                TextComponent.Builder sectionBuilder = section.getBuilder();

                for (ComponentSection hover : section.getHovers())
                    if (user != null && hover.getFilter().test(user))
                        hovers.add(hover.getBuilder().build());

                if (!hovers.isEmpty()) {
                    Component hover = Component.join(JoinConfiguration.newlines(), hovers);
                    sectionBuilder.hoverEvent(HoverEvent.showText(hover));
                }

                builder.append(sectionBuilder);
            }
        }

        return builder.build();
    }

    public void message(@NotNull Audience audience) { MessageUtils.message(audience, this::build); }

    public void broadcast(@NotNull Predicate<User> filter) { MessageUtils.broadcast(filter, this::build); }

    public void broadcast() { MessageUtils.broadcast(this::build); }

    private String getStyleFromPrevious() {
        if (sections.isEmpty())
            return "";

        ComponentSection section = sections.get(sections.size() - 1);
        StringBuilder builder = new StringBuilder();
        Style style = section.getLastStyle();
        TextColor color = style.color();

        if (color != null) {
            NamedTextColor named = NamedTextColor.namedColor(color.value());

            if (named != null)
                builder.append(ColorUtils.fromNamedTextColor(named));
            else {
                String hex = color.asHexString().replace("#", "");
                StringBuilder serialized = new StringBuilder();

                serialized.append("&x");

                for (char c : hex.toCharArray())
                    serialized.append("&").append(c);

                builder.append(serialized);
            }
        }

        for (Map.Entry<TextDecoration, TextDecoration.State> entry : style.decorations().entrySet())
            if (entry.getValue().equals(TextDecoration.State.TRUE))
                builder.append(ColorUtils.fromTextDecoration(entry.getKey()));

        return builder.toString();
    }

}
