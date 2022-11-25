/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.arguments.parser.*;
import xyz.dashnetwork.celest.command.arguments.suggester.PlayerListSuggester;
import xyz.dashnetwork.celest.command.arguments.suggester.PlayerSuggester;
import xyz.dashnetwork.celest.utils.FunctionPair;

import java.util.Collections;
import java.util.List;

public enum ArgumentType {

    CHAT_TYPE(new ChatTypeParser(), (source, string) -> Collections.emptyList()),
    INTEGER(new IntegerParser(), (source, string) -> Collections.emptyList()),
    LONG(new LongParser(), (source, string) -> Collections.emptyList()),
    PLAYER_LIST(new PlayerListParser(), new PlayerListSuggester()),
    PLAYER(new PlayerParser(), new PlayerSuggester()),
    SERVER(new ServerParser(), (source, string) -> Collections.emptyList()),
    UNIQUE_ID(new UniqueIdParser(), (source, string) -> Collections.emptyList()),
    STRING((source, string) -> string, (source, string) -> Collections.emptyList()),
    MESSAGE((source, string) -> string, (source, string) -> Collections.emptyList());

    private final FunctionPair<CommandSource, String, ?> parser;
    private final FunctionPair<CommandSource, String, List<String>> suggester;

    ArgumentType(FunctionPair<CommandSource, String, ?> parser, FunctionPair<CommandSource, String, List<String>> suggester) {
        this.parser = parser;
        this.suggester = suggester;
    }

    public Object parse(CommandSource source, String text) { return parser.apply(source, text); }

    public List<String> suggest(CommandSource source, String input) { return suggester.apply(source, input); }

    public String usage() { return name().toLowerCase().replace("_", "-"); }

}
