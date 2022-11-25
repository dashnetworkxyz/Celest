/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder;

import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.utils.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class Section {

    public static class Hover {

        final String text;
        Predicate<User> predicate;

        public Hover(String text, Predicate<User> predicate) {
            this.text = text;
            this.predicate = predicate;
        }

    }

    final String text;
    List<Hover> hovers;
    ClickEvent click;
    Predicate<User> predicate;

    public Section(String text, Hover[] hover, ClickEvent click, Predicate<User> predicate) {
        this.text = text;
        this.hovers = new ArrayList<>();
        this.click = click;
        this.predicate = predicate;

        if (hover != null)
            hovers.addAll(List.of(hover));
    }

    public Section hover(String hover) {
        hovers.add(new Hover(hover, null));
        return this;
    }

    public Section hover(String hover, Predicate<User> predicate) {
        hovers.add(new Hover(hover, predicate));
        return this;
    }

    public Section click(ClickEvent click) {
        this.click = click;
        return this;
    }

    public Section onlyIf(Predicate<User> predicate) {
        this.predicate = predicate;
        return this;
    }

}