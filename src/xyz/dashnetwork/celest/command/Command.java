/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.ArrayUtils;
import xyz.dashnetwork.celest.utils.PermissionUtils;
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.Messages;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public abstract class Command implements SimpleCommand {

    private static final CommandManager commandManager = Celest.getServer().getCommandManager();
    private ArgumentType[] types;
    private Predicate<User> predicate;
    private boolean console;

    public Command(String label, String... aliases) {
        types = new ArgumentType[0];
        predicate = user -> true;
        console = true;

        CommandMeta.Builder builder = commandManager.metaBuilder(label);
        builder.plugin(Celest.getInstance());
        builder.aliases(aliases);

        commandManager.register(builder.build(), this);
    }

    protected void arguments(ArgumentType... types) { this.types = types; }

    protected void permission(Predicate<User> predicate, boolean console) {
        this.predicate = predicate;
        this.console = console;
    }

    protected abstract void execute(CommandSource source, Arguments arguments);

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        Arguments arguments = new Arguments(source, invocation.arguments(), types);

        if (types.length > 0 && arguments.isMissing()) {
            MessageUtils.message(source, Messages.commandUsage(invocation.alias(),
                    ArrayUtils.convertToString(types, ArgumentType::usage, " ")));
            return;
        }

        execute(source, arguments);
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return PermissionUtils.checkSource(invocation.source(), predicate, console);
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (types.length == 0)
            return CompletableFuture.completedFuture(Collections.emptyList());

        if (args.length == 0)
            return CompletableFuture.completedFuture(types[0].suggest(source, ""));

        if (types.length >= args.length)
            return CompletableFuture.completedFuture(types[args.length - 1].suggest(source, args[args.length - 1]));

        return CompletableFuture.completedFuture(Collections.emptyList());
    }

}
