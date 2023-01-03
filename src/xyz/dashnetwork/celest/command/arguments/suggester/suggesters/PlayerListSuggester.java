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
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class PlayerListSuggester implements Suggester {

    @Override
    public List<String> suggest(User user, String input) {
        List<String> list = new ArrayList<>();
        String[] split = input.split(",");
        int length = split.length - 1;

        if (length < 0)
            return list;

        boolean ends = input.endsWith(",");
        String last = split[length];
        String remaining = StringUtils.unsplit(ends ? 0 : -1, ",", split);

        if (!remaining.isBlank())
            remaining += ",";

        if (ends)
            last = "";

        ListUtils.addIfStarts(list, input, "@a");
        List<String> playerSuggest = ArgumentType.PLAYER.suggest(user, last);

        for (String entry : playerSuggest)
            ListUtils.addIfStarts(list, input, remaining + entry);

        return list;
    }

}
