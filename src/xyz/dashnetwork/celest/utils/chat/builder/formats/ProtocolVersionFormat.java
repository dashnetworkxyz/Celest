/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import com.velocitypowered.api.network.ProtocolVersion;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.VersionUtils;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;

import java.util.ArrayList;
import java.util.List;

public class ProtocolVersionFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public ProtocolVersionFormat(ProtocolVersion version) {
        String hover = "&6Protocol " + version.getProtocol() +
                "\n&7" + ListUtils.convertToString(version.getVersionsSupportedBy(), string -> string, ", ");

        sections.add(new TextSection(VersionUtils.getVersionString(version), hover, null));
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
