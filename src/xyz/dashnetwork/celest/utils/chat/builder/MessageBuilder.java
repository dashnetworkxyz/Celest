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
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;

import java.util.ArrayList;
import java.util.List;

public final class MessageBuilder {

    private final List<Section> sections = new ArrayList<>();

    public Section append(String text) {
        Section section = new Section(text);
        sections.add(section);

        return section;
    }

    public Component build() {
        List<TextComponent> components = new ArrayList<>();

        for (Section section : sections) {
            TextComponent component = ComponentUtils.toComponent(section.text);

            if (section.hover != null)
                component = component.hoverEvent(HoverEvent.showText(ComponentUtils.toComponent(section.hover)));
            if (section.click != null)
                component = component.clickEvent(section.click);

            components.add(component);
        }

        return Component.join(JoinConfiguration.noSeparators(), components);
    }

}
