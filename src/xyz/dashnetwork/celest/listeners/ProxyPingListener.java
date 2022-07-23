/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.google.common.reflect.TypeToken;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import xyz.dashnetwork.celest.User;
import xyz.dashnetwork.celest.utils.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProxyPingListener {

    private static final LegacyComponentSerializer legacy = LegacyComponentSerializer.legacyAmpersand();

    @Subscribe
    public void onProxyPing(ProxyPingEvent event) {
        // String address = event.getConnection().getRemoteAddress().getHostString(); TODO
        ServerPing.Builder builder = event.getPing().asBuilder();
        int online = 0;

        for (User user : User.getUsers())
            if (!user.getData().getVanish())
                online++;

        // TODO: Not at all finished.

        int max = Configuration.getConfig("motd.max").getInt();
        Component description = legacy.deserialize(Configuration.getConfig("motd").getString());
        String software = Configuration.getConfig("motd.software").getString();
        List<String> hover;

        try {
            hover = Configuration.getConfig("motd.hover").getList(TypeToken.of(String.class));
        } catch (ObjectMappingException exception) {
            exception.printStackTrace();
            hover = new ArrayList<>();
        }

        builder.clearMods().clearSamplePlayers();
        builder.onlinePlayers(online);
        builder.maximumPlayers(max);
        builder.description(description);
        builder.version(new ServerPing.Version(builder.getVersion().getProtocol(), software));

        for (String line : hover)
            builder.samplePlayers(new ServerPing.SamplePlayer(line, UUID.randomUUID()));

        event.setPing(builder.build());

        // TODO: Pingspy
    }

}
