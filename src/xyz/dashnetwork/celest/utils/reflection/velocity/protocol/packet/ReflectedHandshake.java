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

package xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet;

import com.velocitypowered.api.network.ProtocolVersion;
import xyz.dashnetwork.celest.utils.reflection.ClassList;

import java.lang.reflect.Method;

public final class ReflectedHandshake {

    private static final Class<?> clazz = ClassList.HANDSHAKE;
    private static final Class<?>[] array = new Class<?>[] { clazz };
    private static Method getProtocolVersion, getServerAddress, getPort, getNextStatus;
    private final Object original;

    static {
        try {
            getProtocolVersion = clazz.getMethod("getProtocolVersion");
            getServerAddress = clazz.getMethod("getServerAddress");
            getPort = clazz.getMethod("getPort");
            getNextStatus = clazz.getMethod("getNextStatus");
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public static Class<?>[] array() { return array; }

    public ReflectedHandshake(Object original) { this.original = original; }

    public Object original() { return original; }

    public ProtocolVersion getProtocolVersion() throws ReflectiveOperationException {
        return (ProtocolVersion) getProtocolVersion.invoke(original);
    }

    public String getServerAddress() throws ReflectiveOperationException {
        return (String) getServerAddress.invoke(original);
    }

    public int getPort() throws ReflectiveOperationException {
        return (int) getPort.invoke(original);
    }

    public int getNextStatus() throws ReflectiveOperationException {
        return (int) getNextStatus.invoke(original);
    }

}
