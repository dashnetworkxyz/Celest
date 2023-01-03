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

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.arguments.ArgumentSection;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ArgumentSectionFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public ArgumentSectionFormat(CommandSource source, Collection<ArgumentSection> list) {
        boolean required = true;

        for (ArgumentSection each : list) {
            if (each.allowsConsole() || source instanceof Player) {
                sections.addAll(new ArgumentSectionFormat(required, each).sections());

                if (required)
                    required = false;
            }
        }
    }

    public ArgumentSectionFormat(boolean required, ArgumentSection section) {
        for (ArgumentType type : section.getArgumentTypes()) {
            TextSection text = new ArgumentTypeFormat(required, type).sections().get(0);
            text.onlyIf(section.getPredicate());

            sections.add(new TextSection(" ", null, section.getPredicate()));
            sections.add(text);
        }
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
