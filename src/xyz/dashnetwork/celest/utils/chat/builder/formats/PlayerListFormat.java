/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerListFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public PlayerListFormat(Collection<Player> players) {
        for (Player player : players) {
            if (!sections.isEmpty())
                sections.add(new TextSection("&6, ", null, null, null));

            sections.addAll(new PlayerFormat(player).sections());
        }
    }

    public PlayerListFormat(Player... players) { this(List.of(players)); }

    @Override
    public List<TextSection> sections() { return sections; }

}
