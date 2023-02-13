/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.command.arguments;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.utils.ArgumentUtils;
import xyz.dashnetwork.celest.utils.LazyUtils;
import xyz.dashnetwork.celest.utils.MathUtils;
import xyz.dashnetwork.celest.utils.StringUtils;
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
        User user = User.getUser(source).orElse(null);
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
