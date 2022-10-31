/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet;

import com.velocitypowered.api.network.ProtocolVersion;
import xyz.dashnetwork.celest.utils.reflection.ClassList;

import java.lang.reflect.Method;

public final class ReflectedHandshake {

    private static final Class<?> clazz;
    private static final Class<?>[] array;
    private static final Method getProtocolVersion, getServerAddress, getPort, getNextStatus;
    private final Object original;

    static {
        try {
            clazz = ClassList.HANDSHAKE;
            array = new Class<?>[] { clazz };

            getProtocolVersion = clazz.getMethod("getProtocolVersion");
            getServerAddress = clazz.getMethod("getServerAddress");
            getPort = clazz.getMethod("getPort");
            getNextStatus = clazz.getMethod("getNextStatus");
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
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
