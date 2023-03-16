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

package xyz.dashnetwork.celest.utils.chat.builder;

import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.utils.CompareUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class TextSection implements Cloneable {

    public static final class Hover {

        final String text;
        Predicate<User> predicate;

        public Hover(String text, Predicate<User> predicate) {
            this.text = text;
            this.predicate = predicate;
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof Hover hover)
                return text.equals(hover.text) && CompareUtils.equalsWithNull(predicate, hover.predicate);

            return false;
        }

    }

    String text;
    String insertion;
    List<Hover> hovers;
    ClickEvent click;
    Predicate<User> predicate;

    public TextSection(String text, String insertion, List<Hover> hovers, ClickEvent click, Predicate<User> predicate) {
        this.text = text;
        this.insertion = insertion;
        this.hovers = hovers;
        this.click = click;
        this.predicate = predicate;
    }

    public TextSection(String text, String hover, Predicate<User> predicate) {
        this(text, null, new ArrayList<>(), null, predicate);

        if (hover != null)
            hover(hover);
    }

    public TextSection hover(String hover) { return hover(hover, null); }

    public TextSection hover(String hover, Predicate<User> predicate) {
        hovers.add(new Hover(hover, predicate));
        return this;
    }

    public TextSection hover(Format format) {
        for (TextSection section : format.sections())
            hover(section.text, section.predicate);
        return this;
    }

    public TextSection click(ClickEvent click) {
        this.click = click;
        return this;
    }

    public TextSection insertion(String insertion) {
        this.insertion = insertion;
        return this;
    }

    public TextSection onlyIf(Predicate<User> predicate) {
        this.predicate = predicate;
        return this;
    }

    @Override
    public TextSection clone() {
        try {
            return (TextSection) super.clone();
        } catch (CloneNotSupportedException exception) {
            throw new RuntimeException();
        }
    }

}