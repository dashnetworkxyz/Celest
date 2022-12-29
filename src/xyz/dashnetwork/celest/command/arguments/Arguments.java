/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.utils.*;
import xyz.dashnetwork.celest.utils.connection.OfflineUser;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public final class Arguments {

    private final List<Object> parsed = new ArrayList<>();
    private boolean playersSuccessfullyParsed = true;
    private int index = 0;

    public Arguments(CommandSource source, String[] array, List<ArgumentSection> sections) {
        User user = CastUtils.toUser(source);
        List<ArgumentType> list = ArgumentUtils.typesFromSections(user, sections);
        int size = MathUtils.getLowest(array.length, list.size());

        for (int i = 0; i < size; i++) {
            ArgumentType argument = list.get(i);
            String string = argument == ArgumentType.MESSAGE ?
                    StringUtils.unsplit(i, " ", array) :
                    array[i];

            Object object = argument.parse(user, string);

            if (object != null)
                parsed.add(object);
            else if (LazyUtils.anyEquals(argument,
                    ArgumentType.PLAYER, ArgumentType.PLAYER_LIST, ArgumentType.OFFLINE_USER))
                playersSuccessfullyParsed = false;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(@NotNull Class<T> clazz) {
        if (parsed.size() <= index)
            return Optional.empty();

        Object object = parsed.get(index);

        if (clazz.isInstance(object)) {
            index++;
            return Optional.of((T) object);
        }

        return Optional.empty();
    }

    public Player playerOrSelf(@NotNull CommandSource source) {
        return getOrSelf(Player.class, source, self -> (Player) self);
    }

    public OfflineUser offlineOrSelf(@NotNull CommandSource source) {
        return getOrSelf(OfflineUser.class, source, self -> User.getUser((Player) self));
    }

    public List<Player> playerListOrSelf(@NotNull CommandSource source) {
        return getListOrSelf(Player[].class, source, self -> (Player) self);
    }

    public List<OfflineUser> offlineListOrSelf(@NotNull CommandSource source) {
        return getListOrSelf(OfflineUser[].class, source, self -> User.getUser((Player) self));
    }

    private <T> T getOrSelf(@NotNull Class<T> clazz, @NotNull CommandSource source, @NotNull Function<CommandSource, T> function) {
        Optional<T> optional = get(clazz);

        if (optional.isEmpty()) {
            if (source instanceof Player && playersSuccessfullyParsed)
                return function.apply(source);

            return null;
        }

        return optional.get();
    }

    private <T> List<T> getListOrSelf(@NotNull Class<T[]> clazz, @NotNull CommandSource source, @NotNull Function<CommandSource, T> function) {
        List<T> list = new ArrayList<>();
        Optional<T[]> optional = get(clazz);

        if (optional.isEmpty()) {
            if (source instanceof Player && playersSuccessfullyParsed)
                list.add(function.apply(source));

            return list;
        }

        list.addAll(List.of(optional.get()));

        return list;
    }

}
