/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.inject.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import xyz.dashnetwork.celest.inject.reflection.velocity.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.inject.reflection.velocity.connection.client.ReflectedHandshakeSessionHandler;
import xyz.dashnetwork.celest.inject.server.handler.CelestHandshakeSessionHandler;

import java.lang.reflect.Method;

public final class CelestServerChannelInitializer extends ChannelInitializer<Channel> {

    private static final Method initChannel;
    private final ChannelInitializer<?> original;

    static {
        try {
            initChannel = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
            initChannel.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public CelestServerChannelInitializer(ChannelInitializer<?> original) { this.original = original; }

    @Override
    protected void initChannel(Channel channel) throws ReflectiveOperationException {
        initChannel.invoke(original, channel);

        ReflectedMinecraftConnection connection = new ReflectedMinecraftConnection(channel.pipeline().get("handler"));
        ReflectedHandshakeSessionHandler handler = new ReflectedHandshakeSessionHandler(connection.getSessionHandler());

        connection.setSessionHandler(new CelestHandshakeSessionHandler(handler, connection));
    }

}
