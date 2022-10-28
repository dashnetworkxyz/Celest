/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.handler;

import com.velocitypowered.api.network.ProtocolVersion;
import io.netty.channel.ChannelHandler;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class ReflectedHandshakeHandler implements InvocationHandler {

    private final Object original;
    private final ChannelHandler channelHandler;

    public ReflectedHandshakeHandler(Object original, ChannelHandler channelHandler) {
        this.original = original;
        this.channelHandler = channelHandler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws ReflectiveOperationException {
        String name = method.getName();
        Class<?>[] types = method.getParameterTypes();

        Method originalMethod = original.getClass().getMethod(name, types);

        try { // TODO: remove try catch when testing is done.
            if (ReflectionUtils.isMethod(originalMethod, "handle", ReflectionUtils.arrayHandshake())) {
                Object handshake = args[0];

                ProtocolVersion version = (ProtocolVersion) ReflectionUtils.invokeMethod(handshake, "getProtocolVersion");
                String hostname = (String) ReflectionUtils.invokeMethod(handshake, "getServerAddress");
                int port = (int) ReflectionUtils.invokeMethod(handshake, "getPort");
                int next = (int) ReflectionUtils.invokeMethod(handshake, "getNextStatus");

                // TODO: CelestHandshakeEvent (allow event cancelling?)

                String cleaned = (String) ReflectionUtils.getDeclaredMethod(original, "cleanVhost", String.class).invoke(original, hostname);
                Enum<?> state = (Enum<?>) ReflectionUtils.getDeclaredMethod(original, "getStateForProtocol", int.class).invoke(original, next);
                Object inbound = ReflectionUtils.constructInitialInboundConnection(channelHandler, cleaned, handshake);

                if (state == null) {
                    Object logger = ReflectionUtils.getDeclaredField(original, "LOGGER");
                    ReflectionUtils.getMethod(logger, "error", String.class, Object.class, Object.class).invoke(logger, "error", "{} provided invalid protocol {}", inbound, next);
                    ReflectionUtils.getMethod(channelHandler, "close", boolean.class).invoke(channelHandler, true);
                } else {
                    ReflectionUtils.getMethod(channelHandler, "setState", ReflectionUtils.classStateRegistry()).invoke(channelHandler, state);
                    ReflectionUtils.getMethod(channelHandler, "setProtocolVersion", ProtocolVersion.class).invoke(channelHandler, version);
                    ReflectionUtils.getMethod(channelHandler, "setAssociation", ReflectionUtils.classMinecraftConnectionAssociation()).invoke(channelHandler, inbound);

                    switch (state.name()) {
                        case "STATUS":
                            // TODO
                            Object statusHandler = ReflectionUtils.constructStatusSessionHandler(Celest.getServer(), inbound);
                            Object instance = Proxy.newProxyInstance(
                                    ReflectionUtils.loaderMinecraftSessionHandler(),
                                    ReflectionUtils.arrayMinecraftSessionHandler(),
                                    new ReflectedStatusHandler(statusHandler, channelHandler)
                            );

                            ReflectionUtils.getMethod(channelHandler, "setSessionHandler", ReflectionUtils.classMinecraftSessionHandler()).invoke(channelHandler, instance);
                            break;
                        case "LOGIN":
                            ReflectionUtils.getDeclaredMethod(channelHandler, "handleLogin", ReflectionUtils.classHandshake(), ReflectionUtils.classInitialInboundConnection()).invoke(channelHandler, handshake, inbound);
                            break;
                        default:
                            throw new AssertionError("getStateForProtocol provided invalid state!");
                    }
                }

                return true;
            } else
                return originalMethod.invoke(original, args);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
            return true;
        }
    }

}
