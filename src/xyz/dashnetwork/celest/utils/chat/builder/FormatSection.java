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

package xyz.dashnetwork.celest.utils.chat.builder;

import xyz.dashnetwork.celest.utils.connection.User;

import java.util.List;
import java.util.function.Predicate;

public final class FormatSection {

    private final List<TextSection> sections;

    public FormatSection(List<TextSection> sections) { this.sections = sections; }

    public void prefix(String string) {
        for (TextSection section : sections)
            section.text = string + section.text;
    }

    public void suffix(String string) {
        for (TextSection section : sections)
            section.text += string;
    }

    public void onlyIf(Predicate<User> predicate) {
        for (TextSection section : sections) {
            if (section.predicate == null)
                section.predicate = predicate;
            else
                section.predicate = section.predicate.and(predicate);
        }
    }

}
