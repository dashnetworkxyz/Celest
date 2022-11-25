/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.suggester;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.utils.FunctionPair;

import java.util.List;

public class PlayerListSuggester implements FunctionPair<CommandSource, String, List<String>> {

    @Override
    public List<String> apply(CommandSource source, String input) {
        // TODO
        return List.of("test2: " + input);
    }

}
