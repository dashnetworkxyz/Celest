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

package xyz.dashnetwork.celest.inject.server.handler;

import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.InboundConnection;
import com.velocitypowered.api.proxy.ProxyServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.inject.api.CelestServerPing;
import xyz.dashnetwork.celest.inject.reflection.velocity.ReflectedVelocityServer;
import xyz.dashnetwork.celest.inject.reflection.velocity.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.inject.reflection.velocity.connection.client.ReflectedInitialInboundConnection;
import xyz.dashnetwork.celest.inject.reflection.velocity.connection.client.ReflectedStatusSessionHandler;
import xyz.dashnetwork.celest.inject.reflection.velocity.protocol.packet.ReflectedStatusRequest;
import xyz.dashnetwork.celest.inject.reflection.velocity.protocol.packet.ReflectedStatusResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class CelestStatusSessionHandler implements InvocationHandler {

    private static final ProxyServer server = Celest.getServer();
    private static final ReflectedVelocityServer velocity = new ReflectedVelocityServer(server);
    private final ReflectedStatusSessionHandler handler;
    private final ReflectedInitialInboundConnection inbound;
    private final ReflectedMinecraftConnection connection;

    public CelestStatusSessionHandler(ReflectedInitialInboundConnection inbound, ReflectedMinecraftConnection connection) throws ReflectiveOperationException {
        this.handler = new ReflectedStatusSessionHandler(inbound);
        this.inbound = inbound;
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws ReflectiveOperationException {
        String name = method.getName();
        Class<?>[] types = method.getParameterTypes();

        if (name.equals("handle") && Arrays.equals(types, ReflectedStatusRequest.array())) {
            if (handler.getPingReceived())
                return true;

            handler.setPingReceived(true);
            velocity.getServerListPingHandler().getInitialPing(inbound)
                    .thenCompose(ping -> server.getEventManager().fire(new ProxyPingEvent((InboundConnection) inbound.original(), ping)))
                    .thenAcceptAsync(
                            (event) -> {
                                try {
                                    StringBuilder json = new StringBuilder();
                                    CelestServerPing ping = new CelestServerPing(event.getPing());
                                    ReflectedVelocityServer.getPingGsonInstance(connection.getProtocolVersion()).toJson(ping, json);

                                    connection.write(new ReflectedStatusResponse(json));
                                } catch (ReflectiveOperationException exception) {
                                    exception.printStackTrace();
                                }
                            },
                            connection.eventLoop())
                    .exceptionally((exception) -> null);

            return true;
        }

        return handler.passOriginalMethod(method, args);
    }

}
