/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class NamedSourceFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public NamedSourceFormat(NamedSource... sources) { this(List.of(sources)); }

    public NamedSourceFormat(Collection<NamedSource> sources) {
        for (NamedSource named : sources) {
            if (!sections.isEmpty())
                sections.add(new TextSection("&6, ", null, null));

            sections.addAll(new NamedSourceFormat(named).sections());
        }
    }

    public NamedSourceFormat(NamedSource source) {
        String username = source.getUsername();
        String displayname = source.getDisplayname();
        TextSection section = new TextSection(displayname, "&6" + username, null);

        if (source instanceof User) {
            User user = (User) source;

            section.hover("\n&7Address: &6" + user.getAddress().getString(), User::showAddress)
                    .click(ClickEvent.suggestCommand(user.getUuid().toString()));
        }

        sections.add(section);
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
