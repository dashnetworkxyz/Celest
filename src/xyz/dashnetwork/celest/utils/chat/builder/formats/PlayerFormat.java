/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class PlayerFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public PlayerFormat(Player player) {
        sections.addAll(new NamedSourceFormat(NamedSource.of(player)).sections());
    }

    public PlayerFormat(Player... players) { this(List.of(players)); }

    public PlayerFormat(Collection<Player> players) {
        for (Player player : players) {
            if (!sections.isEmpty())
                sections.add(new TextSection("&6, ", null, null));

            sections.addAll(new PlayerFormat(player).sections());
        }
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
