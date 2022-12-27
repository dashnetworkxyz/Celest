/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;

import java.util.ArrayList;
import java.util.List;

public final class PlayerProfileFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public PlayerProfileFormat(PlayerProfile... profiles) { this(List.of(profiles)); }

    public PlayerProfileFormat(List<PlayerProfile> profiles) {
        for (PlayerProfile profile : profiles) {
            if (!sections.isEmpty())
                sections.add(new TextSection(", ", null, null));

            String stringUuid = profile.getUuid().toString();

            sections.add(new TextSection(profile.getUsername(), "&6" + stringUuid, null)
                    .click(ClickEvent.suggestCommand(stringUuid)));
        }
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
