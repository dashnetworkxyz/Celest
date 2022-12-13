/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import xyz.dashnetwork.celest.utils.LazyUtils;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;

public final class PreLoginListener {

    @Subscribe
    public void onPreLogin(PreLoginEvent event) {
        ProtocolVersion version = event.getConnection().getProtocolVersion();

        if (LazyUtils.anyEquals(version, ProtocolVersion.MINECRAFT_1_7_2, ProtocolVersion.MINECRAFT_1_7_6)) {
            event.setResult(PreLoginEvent.PreLoginComponentResult.denied(ComponentUtils.fromLegacyString(
                    "&6&lDashNetwork" +
                            "\n\n&61.7&7 is no longer supported." +
                            "\nPlease update to &61.8&l or newer."
            )));
        }
    }

}
