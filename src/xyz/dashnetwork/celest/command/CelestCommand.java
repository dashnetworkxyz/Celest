/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.command;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.arguments.ArgumentSection;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.util.ArgumentUtil;
import xyz.dashnetwork.celest.util.ListUtil;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.formats.AliasesFormat;
import xyz.dashnetwork.celest.chat.builder.formats.ArgumentSectionFormat;
import xyz.dashnetwork.celest.connection.User;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public abstract class CelestCommand implements SimpleCommand {

    private static final CommandManager commandManager = Celest.getServer().getCommandManager();
    private final Map<Integer, String[]> suggestions;
    private final List<ArgumentSection> sections;
    private final List<String> labels;
    private Predicate<User> predicate;
    private boolean console;

    public CelestCommand(String label, String... aliases) {
        suggestions = new HashMap<>();
        sections = new ArrayList<>();
        labels = new ArrayList<>();
        predicate = user -> true;
        console = true;

        CommandMeta.Builder builder = commandManager.metaBuilder(label);
        builder.plugin(Celest.getInstance());

        labels.add(label);

        if (aliases.length > 0) {
            builder.aliases(aliases);
            labels.addAll(List.of(aliases));
        }

        commandManager.register(builder.build(), this);
    }

    protected void setPermission(@NotNull Predicate<User> predicate, boolean console) {
        this.predicate = predicate;
        this.console = console;
    }

    protected void setSuggestions(int place, String... suggestions) { this.suggestions.put(place, suggestions); }

    protected void addArguments(boolean required, @NotNull ArgumentType... types) {
        sections.add(new ArgumentSection(user -> true, true, required, types));
    }

    protected void addArguments(@NotNull Predicate<User> predicate, boolean console, @NotNull ArgumentType... types) {
        sections.add(new ArgumentSection(predicate, console, false, types));
    }

    protected void sendUsage(@NotNull CommandSource source, @NotNull String label) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&c Usage: ");
        builder.append(new AliasesFormat(label, labels)).color(NamedTextColor.AQUA);
        builder.append(new ArgumentSectionFormat(source, sections)).color(NamedTextColor.AQUA);
        builder.message(source);
    }

    protected abstract void execute(CommandSource source, String label, Arguments arguments);

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String alias = invocation.alias();
        Arguments arguments = new Arguments(source, invocation.arguments(), sections);

        if (arguments.hasInvalid())
            return;

        if (!arguments.hasRequired()) {
            sendUsage(source, alias);
            return;
        }

        execute(source, alias, arguments);
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return User.getUser(invocation.source()).map(predicate::test).orElse(console);
    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        Optional<User> optional = User.getUser(invocation.source());

        if (optional.isEmpty())
            return CompletableFuture.completedFuture(Collections.emptyList());

        User user = optional.get();
        String[] args = invocation.arguments();
        List<ArgumentType> list = ArgumentUtil.typesFromSections(user, sections);
        int length = args.length;
        int size = list.size();

        if (size > 0 && size >= length) {
            String selected = length == 0 ? "" : args[length - 1];

            if (length > 0)
                length--;

            if (suggestions.containsKey(length)) {
                List<String> custom = new ArrayList<>();

                for (String each : suggestions.get(length))
                    ListUtil.addIfStarts(custom, selected, each);

                return CompletableFuture.completedFuture(custom);
            }

            return CompletableFuture.completedFuture(list.get(length).suggest(user, selected));
        }

        return CompletableFuture.completedFuture(Collections.emptyList());
    }

}
