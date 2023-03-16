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

import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.connection.OfflineUser;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class OfflineUserFormat implements Format {

    private final List<TextSection> sections;

    public OfflineUserFormat(OfflineUser offline) {
        if (offline.isActive())
            sections = new NamedSourceFormat((User) offline).sections();
        else
            sections = new PlayerProfileFormat(offline.toPlayerProfile()).sections();
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
