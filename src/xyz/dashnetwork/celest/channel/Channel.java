/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.channel;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import com.velocitypowered.api.proxy.messages.ChannelRegistrar;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Channel {

    private static final Map<ChannelIdentifier, Supplier<Channel>> inputMap = new HashMap<>();
    private static final Map<String, Supplier<Channel>> outputMap = new HashMap<>();
    private static final ChannelRegistrar registrar = Celest.getServer().getChannelRegistrar();
    protected final ByteArrayDataOutput output = ByteStreams.newDataOutput();

    public static void registerIn(String name, Supplier<Channel> supplier) {
        ChannelIdentifier identifier = MinecraftChannelIdentifier.create("dashnetwork", name);

        registrar.register(identifier);
        inputMap.put(identifier, supplier);
    }

    public static void registerOut(String name, Supplier<Channel> supplier) {
        registrar.register(MinecraftChannelIdentifier.create("dashnetwork", name));
        outputMap.put(name, supplier);
    }

    public static boolean callIn(ChannelIdentifier identifier, ChannelMessageSink sink, ByteArrayDataInput input) {
        Supplier<Channel> supplier = inputMap.get(identifier);

        if (supplier != null) {
            pluginMessage(identifier, sink, supplier, channel -> channel.handle(input));
            return true;
        }

        return false;
    }

    public static void callOut(String name, ChannelMessageSink sink, User user) {
        Supplier<Channel> supplier = outputMap.get(name);

        if (supplier != null)
            pluginMessage(MinecraftChannelIdentifier.create("dashnetwork", name),
                    sink, supplier, channel -> channel.handle(user));
    }

    private static void pluginMessage(ChannelIdentifier identifier, ChannelMessageSink sink,
                                      Supplier<Channel> supplier, Consumer<Channel> handler) {
        Channel channel = supplier.get();
        handler.accept(channel);

        byte[] array = channel.output.toByteArray();

        if (array.length > 0)
            sink.sendPluginMessage(identifier, array);
    }

    protected void handle(ByteArrayDataInput input) {}

    protected void handle(User user) {}

}
