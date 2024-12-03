/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.sections.ComponentSection;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.OfflineUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class OfflineUserFormat implements Format {

    private final List<ComponentSection> sections;

    public OfflineUserFormat(OfflineUser offline) {
        if (offline.isActive())
            sections = new NamedSourceFormat((User) offline).sections();
        else
            sections = new GameProfileFormat(offline.toGameProfile()).sections();
    }

    public OfflineUserFormat(Collection<OfflineUser> collection, String separator) {
        sections = new ArrayList<>();

        for (OfflineUser offline : collection) {
            if (!sections.isEmpty())
                sections.add(new ComponentSection(separator));

            sections.addAll(new OfflineUserFormat(offline).sections());
        }
    }

    @Override
    public List<ComponentSection> sections() { return sections; }

}
