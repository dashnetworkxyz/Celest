/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.command.arguments.ArgumentSection;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;

import java.util.ArrayList;
import java.util.List;

public class ArgumentSectionFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public ArgumentSectionFormat(CommandSource source, List<ArgumentSection> list) {
        boolean required = true;

        for (ArgumentSection each : list) {
            if (each.allowsConsole() || source instanceof Player) {
                sections.addAll(new ArgumentSectionFormat(required, each).sections());

                if (required)
                    required = false;
            }
        }
    }

    public ArgumentSectionFormat(boolean required, ArgumentSection section) {
        for (ArgumentType type : section.getArgumentTypes()) {
            TextSection text = new ArgumentTypeFormat(required, type).sections().get(0);
            text.onlyIf(section.getPredicate());

            sections.add(new TextSection(" ", null, null, section.getPredicate()));
            sections.add(text);
        }
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
