/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder;

import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.utils.CompareUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class TextSection {

    public static final class Hover {

        final String text;
        Predicate<User> predicate;

        public Hover(String text, Predicate<User> predicate) {
            this.text = text;
            this.predicate = predicate;
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof Hover) {
                Hover hover = (Hover) object;

                return text.equals(hover.text) && CompareUtils.equalsWithNull(predicate, hover.predicate);
            }

            return false;
        }

    }

    String text;
    List<Hover> hovers;
    ClickEvent click;
    Predicate<User> predicate;

    public TextSection(String text, String hover, Predicate<User> predicate) {
        this.text = text;
        this.hovers = new ArrayList<>();
        this.click = null;
        this.predicate = predicate;

        if (hover != null)
            hover(hover);
    }

    public TextSection hover(String hover) {
        hovers.add(new Hover(hover, null));
        return this;
    }

    public TextSection hover(String hover, Predicate<User> predicate) {
        hovers.add(new Hover(hover, predicate));
        return this;
    }

    public TextSection click(ClickEvent click) {
        this.click = click;
        return this;
    }

    public TextSection onlyIf(Predicate<User> predicate) {
        this.predicate = predicate;
        return this;
    }

}