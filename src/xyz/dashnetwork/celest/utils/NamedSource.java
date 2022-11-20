/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;

public final class NamedSource {

    private String username;
    private String displayname;

    public NamedSource(CommandSource source) {
        if (source instanceof Player)
            setupPlayer((Player) source);
        else
            setupConsole();
    }

    private void setupPlayer(Player player) {
        username = player.getUsername();
        displayname = User.getUser(player).getDisplayname();
    }

    private void setupConsole() {
        username = "@CONSOLE";
        displayname = "&4&lConsole&6 CONSOLE";
    }

    public String getUsername() { return username; }

    public String getDisplayname() { return displayname; }

}
