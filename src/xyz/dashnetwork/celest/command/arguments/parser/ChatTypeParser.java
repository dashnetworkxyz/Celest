/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parser;

import xyz.dashnetwork.celest.utils.FunctionPair;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.chat.ChatType;

public final class ChatTypeParser implements FunctionPair<User, String, ChatType> {

    @Override
    public ChatType apply(User user, String string) {
        for (ChatType type : ChatType.values())
            if (type.hasPermission(user) && type.name().equalsIgnoreCase(string))
                return type;

        return null;
    }

}
