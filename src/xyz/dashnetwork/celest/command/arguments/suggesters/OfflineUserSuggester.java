/*
 * Copyright (C) 2023 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.suggesters;

import xyz.dashnetwork.celest.command.arguments.Suggester;
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