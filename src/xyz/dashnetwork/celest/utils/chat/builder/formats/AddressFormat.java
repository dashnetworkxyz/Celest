/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.connection.Address;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public final class AddressFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public AddressFormat(Address address) {
        sections.add(new TextSection("&6", null, null));
        sections.add(new TextSection("&k", null, user -> !user.getData().getSensitiveData()));
        sections.add(new TextSection(address.getString(), null, null));
    }

    public AddressFormat(InetSocketAddress address) {
        sections.add(new TextSection("&6", null, null));
        sections.add(new TextSection("&k", null, user -> !user.getData().getSensitiveData()));
        sections.add(new TextSection(address.getHostString(), null, null));
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
