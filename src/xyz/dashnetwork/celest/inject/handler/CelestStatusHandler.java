/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.handler;

import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.InboundConnection;
import com.velocitypowered.api.proxy.ProxyServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.inject.api.CelestServerPing;
import xyz.dashnetwork.celest.utils.reflection.velocity.ReflectedVelocityServer;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedInitialInboundConnection;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedStatusSessionHandler;
import xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.ReflectedStatusRequest;
import xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.ReflectedStatusResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class CelestStatusHandler implements InvocationHandler {

    private static final ProxyServer server = Celest.getServer();
    private static final ReflectedVelocityServer velocity = new ReflectedVelocityServer(server);
    private final ReflectedStatusSessionHandler handler;
    private final ReflectedInitialInboundConnection inbound;
    private final ReflectedMinecraftConnection connection;

    public CelestStatusHandler(ReflectedInitialInboundConnection inbound, ReflectedMinecraftConnection connection) throws ReflectiveOperationException {
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
                                    ProtocolVersion version = connection.getProtocolVersion();

                                    if (version.getProtocol() > 758)
                                        ping.preventsChatReports(true);

                                    ReflectedVelocityServer.getPingGsonInstance(connection.getProtocolVersion()).toJson(ping, json);
                                    connection.write(new ReflectedStatusResponse(json));
                                } catch (ReflectiveOperationException exception) {
                                    exception.printStackTrace();
                                }
                            },
                            connection.eventLoop())
                    .exceptionally((exception) -> null);

            return true;
        } else
            return handler.passOriginalMethod(method, args);
    }

}
