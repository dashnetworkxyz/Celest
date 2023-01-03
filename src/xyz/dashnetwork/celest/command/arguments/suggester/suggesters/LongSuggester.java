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

package xyz.dashnetwork.celest.command.arguments.suggester.suggesters;

import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.suggester.Suggester;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.TimeType;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class LongSuggester implements Suggester {

    @Override
    public List<String> suggest(User user, String input) {
        List<String> list = new ArrayList<>();

        for (TimeType type : TimeType.values())
            for (String selector : type.getSelectors())
                ListUtils.addIfStarts(list, input, selector);

        list.addAll(ArgumentType.INTEGER.suggest(user, input));

        return list;
    }

}
