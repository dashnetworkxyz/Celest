/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.connection.client;

import xyz.dashnetwork.celest.utils.reflection.ClassList;
import xyz.dashnetwork.celest.utils.reflection.velocity.connection.ReflectedMinecraftSessionHandler;
import xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.ReflectedHandshake;

import java.lang.reflect.Method;

public final class ReflectedHandshakeSessionHandler {

    private static final Class<?> clazz;
    private static Method cleanVhost, getStateForProtocol, handleLogin;
    private final Object original;

    static {
        clazz = ClassList.HANDSHAKE_SESSION_HANDLER;

        try {
            cleanVhost = clazz.getDeclaredMethod("cleanVhost", String.class);
            getStateForProtocol = clazz.getDeclaredMethod("getStateForProtocol", int.class);
            handleLogin = clazz.getDeclaredMethod("handleLogin", ClassList.HANDSHAKE, ClassList.INITIAL_INBOUND_CONNECTION);

            cleanVhost.setAccessible(true);
            getStateForProtocol.setAccessible(true);
            handleLogin.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedHandshakeSessionHandler(ReflectedMinecraftSessionHandler handler) { this.original = handler.original(); }

    public Object passOriginalMethod(Method method, Object[] args) throws ReflectiveOperationException {
        return clazz.getMethod(method.getName(), method.getParameterTypes()).invoke(original, args);
    }

    public String cleanVhost(String hostname) throws ReflectiveOperationException {
        return (String) cleanVhost.invoke(original, hostname);
    }

    public Object getStateForProtocol(int status) throws ReflectiveOperationException {
        return getStateForProtocol.invoke(original, status);
    }

    public void handleLogin(ReflectedHandshake handshake, ReflectedInitialInboundConnection inbound) throws ReflectiveOperationException {
        handleLogin.invoke(original, handshake.original(), inbound.original());
    }

}
