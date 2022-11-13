/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import xyz.dashnetwork.celest.utils.function.PlayerFunction;

import java.util.function.Function;

public final class Arguments {

    // TODO

    public enum Type {
        PLAYER(new PlayerFunction()),
        PLAYER_ARRAY(null),
        SERVER(null),
        CHAT_TYPE(null),
        STRING(null),
        UNIQUE_ID(null),
        MESSAGE(null),
        INTEGER(null),
        LONG(null);

        private final Function<String, ?> function;

        Type(Function<String, ?> function) { this.function = function; }

        private Object apply(String text) { return function.apply(text); }

    }

    public static Arguments parse(String[] text, Type... types) {
        int length = types.length;

        if (text.length < length)
            return null;

        for (int i = 0; i < length; i++) {
            Object object = types[i].apply(text[i]);
        }

        return new Arguments();
    }

}
