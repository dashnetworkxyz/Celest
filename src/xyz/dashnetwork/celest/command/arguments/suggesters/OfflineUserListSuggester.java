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

package xyz.dashnetwork.celest.command.arguments.suggesters;

import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Suggester;
import xyz.dashnetwork.celest.utils.ListUtil;
import xyz.dashnetwork.celest.utils.StringUtil;
import xyz.dashnetwork.celest.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class OfflineUserListSuggester implements Suggester {

    @Override
    public List<String> suggest(User user, String input) {
        List<String> list = new ArrayList<>();
        String[] split = input.split(",");
        int length = split.length - 1;

        if (length < 0)
            return list;

        boolean ends = input.endsWith(",");
        String last = split[length];
        String remaining = StringUtil.unsplit(ends ? 0 : -1, ",", split);

        if (!remaining.isBlank())
            remaining += ",";

        if (ends)
            last = "";

        ListUtil.addIfStarts(list, input, "@a");
        List<String> playerSuggest = ArgumentType.OFFLINE_USER.suggest(user, last);

        for (String entry : playerSuggest)
            ListUtil.addIfStarts(list, input, remaining + entry);

        return list;
    }

}
