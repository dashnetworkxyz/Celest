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

import xyz.dashnetwork.celest.command.arguments.suggester.Suggester;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.storage.Cache;
import xyz.dashnetwork.celest.utils.storage.data.CacheData;

import java.util.ArrayList;
import java.util.List;

public final class OfflineUserSuggester implements Suggester {

    @Override
    public List<String> suggest(User user, String input) {
        List<String> list = new ArrayList<>();

        ListUtils.addIfStarts(list, input, "@s");

        for (CacheData cache : Cache.getCache())
            ListUtils.addIfStarts(list, input, cache.getUsername());

        return list;
    }

}
