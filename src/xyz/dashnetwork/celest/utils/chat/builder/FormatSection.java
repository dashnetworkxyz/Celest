/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder;

import xyz.dashnetwork.celest.utils.connection.User;

import java.util.List;
import java.util.function.Predicate;

public final class FormatSection {

    private final List<TextSection> sections;

    public FormatSection(List<TextSection> sections) { this.sections = sections; }

    public void onlyIf(Predicate<User> predicate) {
        for (TextSection section : sections) {
            if (section.predicate == null)
                section.predicate = predicate;
            else
                section.predicate = section.predicate.and(predicate);
        }
    }

}
