/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils.chat.builder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.dashnetwork.celest.utils.CompareUtils;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class MessageBuilder {

    private final List<TextSection> sections = new ArrayList<>();

    public int length() { return sections.size(); }

    public TextSection append(@NotNull String text) {
        TextSection section = new TextSection(text, null, null);
        sections.add(section);

        return section;
    }

    public FormatSection append(@NotNull Format format) {
        sections.addAll(format.sections());
        return new FormatSection(format.sections());
    }

    public Component build(@Nullable User user) {
        List<Component> components = new ArrayList<>();
        TextSection last = null;

        for (TextSection section : sections) {
            if (checkPredicate(user, section.predicate)) {
                if (last != null) {
                    if (isSimilar(section, last)) {
                        last.text += section.text;
                        continue;
                    } else
                        components.add(toComponent(user, last));
                }

                last = section.copy();
            }
        }

        if (last != null)
            components.add(toComponent(user, last));

        return Component.join(JoinConfiguration.noSeparators(), components);
    }

    private boolean checkPredicate(User user, Predicate<User> predicate) {
        if (user == null || predicate == null)
            return true;

        return predicate.test(user);
    }

    private boolean isSimilar(TextSection section1, TextSection section2) {
        return ListUtils.equals(section1.hovers, section2.hovers) &&
                CompareUtils.equalsWithNull(section1.click, section2.click) &&
                CompareUtils.equalsWithNull(section1.predicate, section2.predicate);
    }

    private Component toComponent(User user, TextSection section) {
        Component component = ComponentUtils.fromLegacyString(section.text);

        if (!section.hovers.isEmpty() && user != null) {
            StringBuilder builder = new StringBuilder();

            for (TextSection.Hover hover : section.hovers)
                if (checkPredicate(user, hover.predicate))
                    builder.append(hover.text);

            if (builder.length() > 0)
                component = component.hoverEvent(HoverEvent.showText(ComponentUtils.fromLegacyString(builder.toString())));
        }

        if (section.click != null && user != null)
            component = component.clickEvent(section.click);

        return component;
    }

}
