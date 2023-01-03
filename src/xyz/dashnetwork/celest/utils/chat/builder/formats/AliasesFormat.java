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

import java.util.ArrayList;
import java.util.List;

public final class AliasesFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public AliasesFormat(String label, List<String> aliases) {
        TextSection text = new TextSection("/" + label, null, null);
        List<String> copy = new ArrayList<>(aliases);
        copy.remove(label);

        if (copy.size() > 0) {
            text.hover("&7Aliases for &6/" + label);

            for (String each : copy)
                text.hover("\n&6/" + each);
        } else
            text.hover("&7No aliases for &6/" + label);

        sections.add(text);
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
