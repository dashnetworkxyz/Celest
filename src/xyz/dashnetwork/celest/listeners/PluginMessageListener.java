/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;

public final class PluginMessageListener {

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        /*
        ChannelMessageSource source = event.getSource();

        if (source instanceof ServerConnection) {
            ServerConnection connection = (ServerConnection) source;
            Channel.Inbound inbound = Channel.getInbound(event.getIdentifier());

            if (inbound != null)
                inbound.read(connection.getServerInfo().getName(), event.dataAsDataStream());
        }

         */

        // IdentifierList class? ex: IdentifierList.DN_ONLINE

        /* TODO
        implement dn requests with ServerLoginPluginMessageEvent?

        wdl:init -> respond with wdl:control
        minecraft:brand -> replace brand name
        bungeecord:main & BungeeCord -> replace PlayerCount to exclude vanished players.

        dn:broadcast - read data with (byte: permissionType, String: message, boolean: json)
        dn:online -> respond to backend with dn:online(int: total, int: vanished).

        PermissionType ids:
        0 - NONE
        1 - STAFF
        2 - ADMIN
        3 - OWNER

        I don't believe anything uses dn:broadcast anyway, so maybe don't implement?
        Lobby plugin requires dn:online
        */
    }

}
