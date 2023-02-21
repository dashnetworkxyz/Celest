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

package xyz.dashnetwork.celest.utils.reflection.velocity.connection;

import com.velocitypowered.api.network.ProtocolVersion;
import io.netty.channel.EventLoop;
import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedInitialInboundConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.ReflectedStatusResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class ReflectedMinecraftConnection {

    private static final Class<?> clazz = ClassList.MINECRAFT_CONNECTION;
    private static Method close, write, eventLoop, getSessionHandler, setSessionHandler, setState, getProtocolVersion, setProtocolVersion, setAssociation;
    private final Object original;

    static {
        try {
            close = clazz.getMethod("close", boolean.class);
            write = clazz.getMethod("write", Object.class);
            eventLoop = clazz.getMethod("eventLoop");
            getSessionHandler = clazz.getMethod("getSessionHandler");
            setSessionHandler = clazz.getMethod("setSessionHandler", ClassList.MINECRAFT_SESSION_HANDLER);
            setState = clazz.getMethod("setState", ClassList.STATE_REGISTRY);
            getProtocolVersion = clazz.getMethod("getProtocolVersion");
            setProtocolVersion = clazz.getMethod("setProtocolVersion", ProtocolVersion.class);
            setAssociation = clazz.getMethod("setAssociation", ClassList.MINECRAFT_CONNECTION_ASSOCIATION);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedMinecraftConnection(Object original) { this.original = original; }

    public Object original() { return original; }

    public void close(boolean markKnown) throws ReflectiveOperationException {
        close.invoke(original, markKnown);
    }

    public void write(ReflectedStatusResponse response) throws ReflectiveOperationException {
        write.invoke(original, response.original());
    }

    public EventLoop eventLoop() throws ReflectiveOperationException {
        return (EventLoop) eventLoop.invoke(original);
    }

    public ReflectedMinecraftSessionHandler getSessionHandler() throws ReflectiveOperationException {
        return new ReflectedMinecraftSessionHandler(getSessionHandler.invoke(original));
    }

    public void setSessionHandler(InvocationHandler handler) throws ReflectiveOperationException {
        setSessionHandler.invoke(original, Proxy.newProxyInstance(
                ReflectedMinecraftSessionHandler.loader(),
                ReflectedMinecraftSessionHandler.array(),
                handler
        ));
    }

    public void setState(Object state) throws ReflectiveOperationException {
        setState.invoke(original, state);
    }

    public ProtocolVersion getProtocolVersion() throws ReflectiveOperationException {
        return (ProtocolVersion) getProtocolVersion.invoke(original);
    }

    public void setProtocolVersion(ProtocolVersion version) throws ReflectiveOperationException {
        setProtocolVersion.invoke(original, version);
    }

    public void setAssociation(ReflectedInitialInboundConnection inbound) throws ReflectiveOperationException {
        setAssociation.invoke(original, inbound.original());
    }

}
