/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.utils.*;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Arguments {

    private final List<Object> parsed = new ArrayList<>();
    private int index = 0;

    public Arguments(CommandSource source, String[] array, List<ArgumentSection> sections) {
        User user = CastUtils.toUser(source);
        List<ArgumentType> list = ArgumentUtils.typesFromSections(source, sections);
        int size = MathUtils.getLowest(array.length, list.size());

        for (int i = 0; i < size; i++) {
            ArgumentType argument = list.get(i);
            String string = argument == ArgumentType.MESSAGE ?
                    StringUtils.unsplit(i, " ", array) :
                    array[i];

            Object object = argument.parse(user, string);

            if (object != null)
                parsed.add(object);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(Class<T> clazz) {
        if (parsed.size() <= index)
            return Optional.empty();

        Object object = parsed.get(index);

        if (clazz.isInstance(object)) {
            index++;
            return Optional.of((T) object);
        }

        return Optional.empty();
    }

}
