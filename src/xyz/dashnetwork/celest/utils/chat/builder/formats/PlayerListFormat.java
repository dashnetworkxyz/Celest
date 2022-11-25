/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.Section;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerListFormat implements Format {

    private final List<Section> sections = new ArrayList<>();

    public PlayerListFormat(Collection<Player> players) {
        for (Player player : players) {
            if (!sections.isEmpty())
                sections.add(new Section("&6, ", null, null, null));

            sections.addAll(new PlayerFormat(player).sections());
        }
    }

    @Override
    public List<Section> sections() { return sections; }

}
