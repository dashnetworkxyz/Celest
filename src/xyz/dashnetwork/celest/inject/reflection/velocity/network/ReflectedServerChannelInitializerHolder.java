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

package xyz.dashnetwork.celest.inject.reflection.velocity.network;

import io.netty.channel.ChannelInitializer;
import xyz.dashnetwork.celest.inject.reflection.ClassList;

import java.lang.reflect.Field;

public final class ReflectedServerChannelInitializerHolder {

    private static final Class<?> clazz = ClassList.SERVER_CHANNEL_INITIALIZER_HOLDER;
    private static Field initializer; // Use field to avoid warning message.
    private final Object original;

    static {
        try {
            initializer = clazz.getDeclaredField("initializer");
            initializer.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedServerChannelInitializerHolder(Object original) { this.original = original; }

    public ChannelInitializer<?> get() throws ReflectiveOperationException {
        return (ChannelInitializer<?>) initializer.get(original);
    }

    public void set(ChannelInitializer<?> channelInitializer) throws ReflectiveOperationException {
        initializer.set(original, channelInitializer);
    }

}
