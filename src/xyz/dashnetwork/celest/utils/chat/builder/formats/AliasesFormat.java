/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;

import java.util.ArrayList;
import java.util.List;

public class AliasesFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public AliasesFormat(String label, List<String> aliases) {
        TextSection text = new TextSection("&7/" + label, null, null, null);
        List<String> copy = new ArrayList<>(aliases);
        copy.remove(label);

        if (copy.size() > 0) {
            text.hover("&7Aliases for &6/" + label);

            for (String each : copy)
                text.hover("\n&6/" + each);
        } else
            text.hover("&7No aliases for &6/" + label);

        sections.add(text);
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
