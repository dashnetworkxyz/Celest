/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.Section;

import java.util.ArrayList;
import java.util.List;

public final class PlayerFormat implements Format {

    private final List<Section> sections = new ArrayList<>();

    public PlayerFormat(Player player) {
        setup(player.getUsername(), User.getUser(player).getDisplayname(), player.getRemoteAddress().getHostString());
    }

    public PlayerFormat(User user) {
        setup(user.getUsername(), user.getDisplayname(), user.getAddress().getString());
    }

    public PlayerFormat(NamedSource source) {
        String username = source.getUsername();
        String displayname = source.getDisplayname();

        if (source.isConsole())
            setup(username, displayname, null);
        else {
            String address = ((Player) source.getSource()).getRemoteAddress().getHostString();

            setup(username, displayname, address);
        }
    }

    private void setup(String username, String displayname, String address) {
        Section section = new Section(displayname, null, null, null);
        section.hover("&6" + username);

        if (address != null)
            section.hover("\n&7Address: &6" + address, User::isAdmin);

        sections.add(section);
    }

    @Override
    public List<Section> sections() { return sections; }

}
