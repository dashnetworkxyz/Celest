 /*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class MessageBuilder {

    private final List<Section> sections = new ArrayList<>();

    public Section append(@NotNull String text) {
        Section section = new Section(text, null, null, user -> true);
        sections.add(section);

        return section;
    }

    public FormatSection append(@NotNull Format format) {
        sections.addAll(format.sections());
        return new FormatSection(format.sections());
    }

    public Component build(User user) {
        List<TextComponent> components = new ArrayList<>();

        for (Section section : sections) {
            if (!check(user, section.predicate))
                continue;

            TextComponent component = ComponentUtils.toComponent(section.text);

            if (!section.hovers.isEmpty()) {
                StringBuilder builder = new StringBuilder();

                for (Section.Hover hover : section.hovers)
                    if (check(user, hover.predicate))
                        builder.append(hover.text);

                component = component.hoverEvent(HoverEvent.showText(ComponentUtils.toComponent(builder.toString())));
            } if (section.click != null)
                component = component.clickEvent(section.click);

            components.add(component);
        }

        return Component.join(JoinConfiguration.noSeparators(), components);
    }

    private boolean check(User user, Predicate<User> predicate) {
        if (user == null || predicate == null)
            return true;

        return predicate.test(user);
    }

}
