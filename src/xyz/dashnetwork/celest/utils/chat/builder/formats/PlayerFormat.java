/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.connection.Address;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;

import java.util.ArrayList;
import java.util.List;

public final class PlayerFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public PlayerFormat(Player player) { this(NamedSource.of(player)); }

    public PlayerFormat(NamedSource source) {
        String username = source.getUsername();
        String displayname = source.getDisplayname();
        Address address = source.getAddress();

        TextSection section = new TextSection(displayname, null, null, null);
        section.hover("&6" + username);

        if (address != null)
            section.hover("\n&7Address: &6" + address.getString(), User::isAdmin);

        sections.add(section);
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
