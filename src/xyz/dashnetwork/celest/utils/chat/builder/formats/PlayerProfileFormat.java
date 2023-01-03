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
