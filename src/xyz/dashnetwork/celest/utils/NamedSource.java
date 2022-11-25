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

    private final CommandSource source;
    private boolean console;
    private String username;
    private String displayname;

    public NamedSource(CommandSource source) {
        this.source = source;

        if (source instanceof Player)
            setupPlayer((Player) source);
        else
            setupConsole();
    }

    private void setupPlayer(Player player) {
        console = false;
        username = player.getUsername();
        displayname = User.getUser(player).getDisplayname();
    }

    private void setupConsole() {
        console = true;
        username = "@CONSOLE";
        displayname = "&4&lServer&6 CONSOLE";
    }

    public CommandSource getSource() { return source; }

    public boolean isConsole() { return console; }

    public String getUsername() { return username; }

    public String getDisplayname() { return displayname; }

}
