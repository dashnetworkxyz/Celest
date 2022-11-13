/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder;

import net.kyori.adventure.text.event.ClickEvent;

public final class Section {

    final String text;
    String hover;
    ClickEvent click;

    Section(String text) {
        this.text = text;
        this.hover = null;
        this.click = null;
    }

    public void hover(String hover) { this.hover = hover; }

    public void click(ClickEvent click) { this.click = click; }

}