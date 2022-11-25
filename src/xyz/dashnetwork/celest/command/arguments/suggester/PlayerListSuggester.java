/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.suggester;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.utils.FunctionPair;
import xyz.dashnetwork.celest.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerListSuggester implements FunctionPair<CommandSource, String, List<String>> {

    @Override
    public List<String> apply(CommandSource source, String input) {
        List<String> list = new CopyOnWriteArrayList<>();
        String[] split = input.split(",");
        int length = split.length;
        String last = split[length - 1];

        ListUtils.addIfStarts(list, input, "@a");
        list.addAll(ArgumentType.PLAYER.suggest(source, last));

        if (length > 1) {
            for (int i = length - 1; i >= 0; i--) {
                final int index = i;
                list.forEach(string -> string = split[index] + string);
            }
        }

        return list;
    }

}
