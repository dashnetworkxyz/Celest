/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.arguments.ArgumentSection;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;

import java.util.ArrayList;
import java.util.List;

public final class ArgumentsUtils {

    public static List<ArgumentType> typesFromSections(CommandSource source, List<ArgumentSection> sections) {
        List<ArgumentType> list = new ArrayList<>();
        User user = CastUtils.toUser(source);

        for (ArgumentSection section : sections)
            if (user == null ? section.allowsConsole() : section.getPredicate().test(user))
                list.addAll(List.of(section.getArgumentTypes()));

        return list;
    }

}
