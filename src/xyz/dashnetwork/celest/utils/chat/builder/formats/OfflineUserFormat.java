/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.connection.OfflineUser;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OfflineUserFormat implements Format {

    private final List<TextSection> sections;

    public OfflineUserFormat(OfflineUser offline) {
        if (offline instanceof User)
            sections = new NamedSourceFormat((User) offline).sections();
        else
            sections = new PlayerProfileFormat(offline).sections();
    }

    public OfflineUserFormat(OfflineUser... offlines) { this(List.of(offlines)); }

    public OfflineUserFormat(Collection<OfflineUser> offlines) {
        sections = new ArrayList<>();

        for (OfflineUser each : offlines) {
            if (!sections.isEmpty())
                sections.add(new TextSection("&6, ", null, null));

            sections.addAll(new OfflineUserFormat(each).sections);
        }
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
