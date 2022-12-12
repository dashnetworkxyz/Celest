/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.parser.parsers;

import xyz.dashnetwork.celest.command.arguments.parser.ArgumentParser;
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.connection.User;

public final class ChatTypeParser implements ArgumentParser {

    @Override
    public Object parse(User user, String string) {
        for (ChatType type : ChatType.values())
            if (type.hasPermission(user) && type.name().equalsIgnoreCase(string))
                return type;

        return null;
    }

}
