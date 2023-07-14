/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class AliasesFormat implements Format {

    private final List<ComponentSection> sections = new ArrayList<>();

    public AliasesFormat(String label, Collection<String> aliases) {
        ComponentSection section = new ComponentSection("/" + label);
        List<String> copy = new ArrayList<>(aliases);
        copy.remove(label);

        if (copy.size() > 0) {
            section.hover("&7Aliases for &6/" + label + "&7:");

            for (String each : copy)
                section.hover("&6/" + each);
        } else
            section.hover("&7No aliases for &6/" + label);
    }

    @Override
    public List<ComponentSection> sections() { return sections; }

}
