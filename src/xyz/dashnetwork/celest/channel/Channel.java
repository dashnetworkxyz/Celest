/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.channel;

import com.google.common.io.ByteArrayDataInput;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.Celest;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class Channel {

    public interface Inbound { void read(String serverName, ByteArrayDataInput input); }

    public interface Outbound { void write(ChannelMessageSink sink); }

    private static final Map<ChannelIdentifier, Supplier<Inbound>> inboundMap = new HashMap<>();

    public static void registerInbound(@NotNull String channel, @NotNull Supplier<Inbound> inbound) {
        ChannelIdentifier identifier = MinecraftChannelIdentifier.create("dashnetwork", channel);
        Celest.getServer().getChannelRegistrar().register(identifier);

        inboundMap.put(identifier, inbound);
    }

    public static Inbound getInbound(ChannelIdentifier identifier) {
        Supplier<Inbound> supplier = inboundMap.get(identifier);

        if (supplier == null)
            return null;

        return supplier.get();
    }

}
