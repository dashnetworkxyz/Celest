/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import xyz.dashnetwork.celest.utils.*;

public class CommandTest implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();

        if (source.hasPermission("dashnetwork.owner")) {
            for (CacheData cache : Cache.getCache())
                MessageUtils.message(source, cache.getUUID() + " " + cache.getUsername() + " " + cache.getAddress());

            for (UserData data : Storage.readAll(Storage.Directory.USERDATA, UserData.class))
                MessageUtils.message(source, "" + data.getNickname());
        }
    }

}
