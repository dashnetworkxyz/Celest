/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import xyz.dashnetwork.celest.utils.VersionUtils;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;

public final class PreLoginListener {

    @Subscribe
    public void onPreLogin(PreLoginEvent event) {
        if (VersionUtils.isLegacy(event.getConnection().getProtocolVersion())) {
            event.setResult(PreLoginEvent.PreLoginComponentResult.denied(ComponentUtils.fromLegacyString(
                    "&6&lDashNetwork" +
                            "\n&61.7&7 is no longer supported." +
                            "\nPlease update to &61.8 or newer."
            )));
        }
    }

}
