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

package xyz.dashnetwork.celest.command.arguments;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.OfflineUser;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

public final class Arguments {

    private final List<Object> parsed = new ArrayList<>();
    private final boolean empty;
    private boolean required = true;
    private boolean invalid = false;
    private int index = 0;

    public Arguments(CommandSource source, String[] array, List<ArgumentSection> sections) {
        empty = array.length == 0;

        User user = User.getUser(source).orElse(null);
        int index = 0;

        for (ArgumentSection section : sections) {
            for (ArgumentType type : section.types()) {
                if (array.length <= index) {
                    if (section.required())
                        required = false;

                    return;
                }

                String string = type == ArgumentType.MULTI_STRING ?
                        StringUtils.unsplit(index, " ", array) :
                        array[index];

                index++;
                Object object = type.parse(user, string);

                if (object == null) {
                    MessageUtils.message(source, "&6&l»&7 Couldn't find " + type.getName() + " for &6" + string);
                    invalid = true;
                    return;
                }

                parsed.add(object);
            }
        }
    }

    // TODO
    public boolean isEmpty() { return empty; }

    public boolean hasRequired() { return required; }

    public boolean hasInvalid() { return invalid; }

    @SuppressWarnings("unchecked")
    public <T> T required(@NotNull Class<T> clazz) {
        if (parsed.size() <= index)
            throw new NoSuchElementException("Required argument doesn't exist.");

        Object object = parsed.get(index);

        if (object == null) {
            index++;
            throw new NoSuchElementException("Required argument doesn't exist.");
        }

        if (clazz.isInstance(object)) {
            index++;
            return (T) object;
        }

        throw new NoSuchElementException("Required argument doesn't exist.");
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> optional(@NotNull Class<T> clazz) {
        if (parsed.size() <= index)
            return Optional.empty();

        Object object = parsed.get(index);

        if (object == null) {
            index++;
            return Optional.empty();
        }

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
        Optional<T> optional = optional(clazz);

        if (optional.isEmpty()) {
            if (source instanceof Player)
                return function.apply(source);

            return null;
        }

        return optional.get();
    }

    private <T> List<T> getListOrSelf(@NotNull Class<T[]> clazz, @NotNull CommandSource source, @NotNull Function<CommandSource, T> function) {
        List<T> list = new ArrayList<>();
        Optional<T[]> optional = optional(clazz);

        if (optional.isEmpty()) {
            if (source instanceof Player)
                list.add(function.apply(source));

            return list;
        }

        list.addAll(List.of(optional.get()));

        return list;
    }

}
