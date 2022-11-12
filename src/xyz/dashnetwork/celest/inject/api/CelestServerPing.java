/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.api;

import com.velocitypowered.api.proxy.server.ServerPing;
import com.velocitypowered.api.util.Favicon;
import com.velocitypowered.api.util.ModInfo;
import net.kyori.adventure.text.Component;

public class CelestServerPing {

    private final ServerPing.Version version;
    private final ServerPing.Players players;
    private final Component description;
    private final Favicon favicon;
    private final ModInfo modinfo;
    private Boolean preventsChatReports;

    public CelestServerPing(ServerPing original) {
        this.version = original.getVersion();
        this.players = original.getPlayers().orElse(null);
        this.description = original.getDescriptionComponent();
        this.favicon = original.getFavicon().orElse(null);
        this.modinfo = original.getModinfo().orElse(null);
    }

    public void preventsChatReports(boolean option) {
        if (option)
            preventsChatReports = true;
        else
            preventsChatReports = null;
    }

}
