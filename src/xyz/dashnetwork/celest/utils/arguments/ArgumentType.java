/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.arguments;

import xyz.dashnetwork.celest.utils.arguments.function.*;

import java.util.function.Function;

public enum ArgumentType {

    PLAYER(new PlayerFunction()),
    PLAYER_LIST(new PlayerListFunction()),
    SERVER(new ServerFunction()),
    CHAT_TYPE(new ChatTypeFunction()),
    UNIQUE_ID(new UniqueIdFunction()),
    INTEGER(new IntegerFunction()),
    LONG(new LongFunction()),
    STRING(string -> string),
    MESSAGE(string -> string);

    private final Function<String, ?> function;

    ArgumentType(Function<String, ?> function) { this.function = function; }

    public Object apply(String text) { return function.apply(text); }

}
