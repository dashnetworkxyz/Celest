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

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.arguments.ArgumentSection;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.sections.ComponentSection;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public final class ArgumentSectionFormat implements Format {

    private final List<ComponentSection> sections = new ArrayList<>();

    public ArgumentSectionFormat(ArgumentSection argument) {
        for (ArgumentType type : argument.types()) {
            Predicate<User> predicate = argument.predicate();
            List<ComponentSection> list = new ArrayList<>();
            list.add(new ComponentSection(" "));
            list.addAll(new ArgumentTypeFormat(type, argument.required()).sections());

            for (ComponentSection each : list)
                each.filter(predicate);

            sections.addAll(list);
        }
    }

    public ArgumentSectionFormat(CommandSource source, Collection<ArgumentSection> list) {
        for (ArgumentSection each : list)
            if (each.console() || source instanceof Player)
                sections.addAll(new ArgumentSectionFormat(each).sections());
    }

    @Override
    public List<ComponentSection> sections() { return sections; }

}
