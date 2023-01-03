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

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class PlayerFormat implements Format {

    private final List<TextSection> sections;

    public PlayerFormat(Player player) { sections = new NamedSourceFormat(NamedSource.of(player)).sections(); }

    public PlayerFormat(Player... players) { this(List.of(players)); }

    public PlayerFormat(Collection<Player> players) {
        sections = new ArrayList<>();

        for (Player player : players) {
            if (!sections.isEmpty())
                sections.add(new TextSection("&6, ", null, null));

            sections.addAll(new PlayerFormat(player).sections());
        }
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
