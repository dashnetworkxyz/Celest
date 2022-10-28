/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class ReflectionUtils {

    // TODO: Complete revamp of entire reflection util system

    private static final Class<?> minecraftConnection, minecraftSessionHandler, handshake, statusRequest, initialInboundConnection, statusSessionHandler, stateRegistry, minecraftConnectionAssociation, velocityInboundConnection;
    private static final Class<?>[] minecraftSessionHandlerArray, handshakeArray, statusRequestArray;
    private static final ClassLoader minecraftSessionHandlerLoader;
    private static final Method initChannel, getSessionHandler, setSessionHandler, getState;

    static {
        try { // More RAM, but vroom vroom
            minecraftConnection = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnection");
            minecraftSessionHandler = Class.forName("com.velocitypowered.proxy.connection.MinecraftSessionHandler");
            handshake = Class.forName("com.velocitypowered.proxy.protocol.packet.Handshake");
            statusRequest = Class.forName("com.velocitypowered.proxy.protocol.packet.StatusRequest");
            initialInboundConnection = Class.forName("com.velocitypowered.proxy.connection.client.InitialInboundConnection");
            statusSessionHandler = Class.forName("com.velocitypowered.proxy.connection.client.StatusSessionHandler");
            stateRegistry = Class.forName("com.velocitypowered.proxy.protocol.StateRegistry");
            minecraftConnectionAssociation = Class.forName("com.velocitypowered.proxy.connection.MinecraftConnectionAssociation");
            velocityInboundConnection = Class.forName("com.velocitypowered.proxy.connection.util.VelocityInboundConnection");

            minecraftSessionHandlerArray = new Class<?>[] { minecraftSessionHandler };
            handshakeArray = new Class<?>[] { handshake };
            statusRequestArray = new Class<?>[] { statusRequest };

            minecraftSessionHandlerLoader = minecraftSessionHandler.getClassLoader();

            initChannel = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
            getSessionHandler = minecraftConnection.getMethod("getSessionHandler");
            setSessionHandler = minecraftConnection.getMethod("setSessionHandler", minecraftSessionHandler);
            getState = minecraftConnection.getMethod("getState");

            initChannel.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Class<?> classMinecraftSessionHandler() { return minecraftSessionHandler; }

    public static Class<?> classHandshake() { return handshake; }

    public static Class<?> classInitialInboundConnection() { return initialInboundConnection; }

    public static Class<?> classStateRegistry() { return stateRegistry; }

    public static Class<?> classMinecraftConnectionAssociation() { return minecraftConnectionAssociation; }

    public static Class<?>[] arrayMinecraftSessionHandler() { return minecraftSessionHandlerArray; }

    public static Class<?>[] arrayHandshake() { return handshakeArray; }

    public static Class<?>[] arrayStatusRequest() { return statusRequestArray; }

    public static ClassLoader loaderMinecraftSessionHandler() { return minecraftSessionHandlerLoader; }

    public static Object constructInitialInboundConnection(Object connection, String address, Object object) throws ReflectiveOperationException {
        Constructor<?> constructor = initialInboundConnection.getDeclaredConstructor(minecraftConnection, String.class, handshake);
        constructor.setAccessible(true);

        return constructor.newInstance(connection, address, object);
    }

    public static Object constructStatusSessionHandler(Object server, Object inbound) throws ReflectiveOperationException {
        Constructor<?> constructor = statusSessionHandler.getDeclaredConstructor(server.getClass(), velocityInboundConnection);
        constructor.setAccessible(true);

        return constructor.newInstance(server, inbound);
    }

    public static void invokeInitChannel(Object object, Channel channel) throws ReflectiveOperationException {
        initChannel.invoke(object, channel); // return void
    }

    public static Object invokeGetSessionHandler(Object object) throws ReflectiveOperationException {
        return getSessionHandler.invoke(object);
    }

    public static void invokeSetSessionHandler(Object object, Object sessionHandler) throws ReflectiveOperationException {
        setSessionHandler.invoke(object, sessionHandler); // returns void
    }

    public static Object invokeGetState(Object object) throws ReflectiveOperationException {
        return getState.invoke(object);
    }

    public static Method getMethod(Object object, String name, Class<?>... parameters) throws ReflectiveOperationException {
        return object.getClass().getMethod(name, parameters);
    }

    public static Method getDeclaredMethod(Object object, String name, Class<?>... parameters) throws ReflectiveOperationException {
        Method method = object.getClass().getDeclaredMethod(name, parameters);
        method.setAccessible(true);

        return method;
    }

    public static Object invokeMethod(Object object, String name) throws ReflectiveOperationException {
        return getMethod(object, name).invoke(object);
    }

    public static Object invokeDeclaredMethod(Object object, String name) throws ReflectiveOperationException {
        return getDeclaredMethod(object, name).invoke(object);
    }

    public static Object getDeclaredField(Object object, String name) throws ReflectiveOperationException {
        Field field = object.getClass().getDeclaredField(name);
        field.setAccessible(true);

        return field.get(object);
    }

    public static void setDeclaredField(Object object, String name, Object set) throws ReflectiveOperationException {
        Field field = object.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(object, set);
    }

    public static boolean isMethod(Method method, String name, Class<?>[] array) {
        if (!method.getName().equals(name))
            return false;

        return Arrays.equals(method.getParameterTypes(), array);
    }

}
