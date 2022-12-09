/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.arguments.parser.*;
import xyz.dashnetwork.celest.command.arguments.suggester.ChatTypeSuggester;
import xyz.dashnetwork.celest.command.arguments.suggester.IntegerSuggester;
import xyz.dashnetwork.celest.command.arguments.suggester.PlayerArraySuggester;
import xyz.dashnetwork.celest.command.arguments.suggester.PlayerSuggester;
import xyz.dashnetwork.celest.utils.FunctionPair;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Collections;
import java.util.List;

public enum ArgumentType {

    CHAT_TYPE(new ChatTypeParser(), new ChatTypeSuggester()),
    INTEGER(new IntegerParser(), new IntegerSuggester()),
    LONG(new LongParser(), INTEGER.suggester), // TODO: Long suggester with TimeType selectors
    PLAYER_ARRAY(new PlayerArrayParser(), new PlayerArraySuggester()),
    PLAYER(new PlayerParser(), new PlayerSuggester()),
    SERVER(new ServerParser(), (source, string) -> Collections.emptyList()),
    UNIQUE_ID(new UniqueIdParser(), (source, string) -> Collections.emptyList()),
    STRING((source, string) -> string, (source, string) -> Collections.emptyList()),
    MESSAGE((source, string) -> string, (source, string) -> Collections.emptyList());

    private final FunctionPair<CommandSource, String, ?> parser;
    private final FunctionPair<User, String, List<String>> suggester;

    ArgumentType(FunctionPair<CommandSource, String, ?> parser, FunctionPair<User, String, List<String>> suggester) {
        this.parser = parser;
        this.suggester = suggester;
    }

    public Object parse(CommandSource source, String text) { return parser.apply(source, text); }

    public List<String> suggest(User user, String input) { return suggester.apply(user, input); }

}
