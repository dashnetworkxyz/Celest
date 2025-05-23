/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.channel;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import com.velocitypowered.api.proxy.messages.ChannelRegistrar;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.connection.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Channel {

    private static final Map<ChannelIdentifier, Supplier<Channel>> inputMap = new HashMap<>();
    private static final Map<String, Supplier<Channel>> outputMap = new HashMap<>();
    private static final ChannelRegistrar registrar = Celest.getServer().getChannelRegistrar();

    @SuppressWarnings("UnstableApiUsage")
    protected final ByteArrayDataOutput output = ByteStreams.newDataOutput();
    protected String server = "Unknown";

    public static void registerIn(String name, Supplier<Channel> supplier) {
        ChannelIdentifier identifier = MinecraftChannelIdentifier.create("dn", name);

        registrar.register(identifier);
        inputMap.put(identifier, supplier);
    }

    public static void registerOut(String name, Supplier<Channel> supplier) {
        registrar.register(MinecraftChannelIdentifier.create("dn", name));
        outputMap.put(name, supplier);
    }

    public static boolean callIn(ChannelIdentifier identifier, ChannelMessageSink sink, ByteArrayDataInput input) {
        Supplier<Channel> supplier = inputMap.get(identifier);

        if (supplier == null)
            return false;

        call(identifier, sink, supplier, channel -> channel.handle(input));
        return true;
    }

    public static void callOut(String name, User user) {
        Optional<ServerConnection> optional = user.getPlayer().getCurrentServer();

        if (optional.isEmpty())
            return;

        Supplier<Channel> supplier = outputMap.get(name);

        if (supplier != null)
            call(MinecraftChannelIdentifier.create("dn", name),
                    optional.get().getServer(), supplier, channel -> channel.handle(user));
    }

    private static void call(ChannelIdentifier identifier, ChannelMessageSink sink,
                                      Supplier<Channel> supplier, Consumer<Channel> handler) {
        Channel channel = supplier.get();

        if (sink instanceof ServerConnection connection)
            channel.server = connection.getServerInfo().getName();

        handler.accept(channel);

        byte[] array = channel.output.toByteArray();

        if (array.length > 0)
            sink.sendPluginMessage(identifier, array);
    }

    protected void handle(ByteArrayDataInput input) {}

    protected void handle(User user) {}

}
