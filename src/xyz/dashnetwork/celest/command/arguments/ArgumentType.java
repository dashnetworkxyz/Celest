/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments;

import xyz.dashnetwork.celest.command.arguments.parser.*;
import xyz.dashnetwork.celest.command.arguments.suggester.*;
import xyz.dashnetwork.celest.utils.FunctionPair;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.connection.User;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public enum ArgumentType {

    CHAT_TYPE(new ChatTypeParser(), new ChatTypeSuggester()),
    INTEGER(new IntegerParser(), new IntegerSuggester()),
    LONG(new LongParser(), INTEGER.suggester), // TODO: Long suggester with TimeType selectors
    PLAYER(new PlayerParser(), new PlayerSuggester()),
    PLAYER_LIST(new PlayerListParser(), new PlayerListSuggester()),
    SERVER(new ServerParser(), new ServerSuggester()),
    UNIQUE_ID(new UniqueIdParser(), (user, string) -> Collections.emptyList()),
    STRING((user, string) -> string, (user, string) -> Collections.emptyList()),
    MESSAGE((user, string) -> string, (user, string) -> Collections.emptyList());

    private final FunctionPair<User, String, ?> parser;
    private final FunctionPair<User, String, List<String>> suggester;

    ArgumentType(FunctionPair<User, String, ?> parser, FunctionPair<User, String, List<String>> suggester) {
        this.parser = parser;
        this.suggester = suggester;
    }

    public Object parse(User user, String text) { return parser.apply(user, text); }

    public List<String> suggest(User user, String input) { return suggester.apply(user, input); }

}
