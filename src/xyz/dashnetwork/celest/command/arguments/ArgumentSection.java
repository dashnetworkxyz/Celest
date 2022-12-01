/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments;

import xyz.dashnetwork.celest.utils.User;

import java.util.function.Predicate;

public final class ArgumentSection {

    private final Predicate<User> predicate;
    private final boolean console;
    private final ArgumentType[] types;

    public ArgumentSection(Predicate<User> predicate, boolean console, ArgumentType[] types) {
        this.predicate = predicate;
        this.console = console;
        this.types = types;
    }

    public Predicate<User> getPredicate() { return predicate; }

    public boolean allowsConsole() { return console; }

    public ArgumentType[] getArgumentTypes() { return types; }

}
