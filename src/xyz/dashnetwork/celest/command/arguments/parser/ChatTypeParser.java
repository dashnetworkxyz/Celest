/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parser;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.utils.CastUtils;
import xyz.dashnetwork.celest.utils.FunctionPair;
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.chat.ChatType;

public final class ChatTypeParser implements FunctionPair<CommandSource, String, ChatType> {

    @Override
    public ChatType apply(CommandSource source, String string) {
        User user = CastUtils.toUser(source);

        for (ChatType type : ChatType.values())
            if (type.hasPermission(user) && type.name().equalsIgnoreCase(string))
                return type;

        return null;
    }

}
