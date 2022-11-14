/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.arguments.function;

import xyz.dashnetwork.celest.utils.chat.ChatType;

import java.util.function.Function;

public final class ChatTypeFunction implements Function<String, ChatType> {

    @Override
    public ChatType apply(String string) {
        for (ChatType type : ChatType.values())
            if (type.name().equalsIgnoreCase(string))
                return type;

        return null;
    }

}
