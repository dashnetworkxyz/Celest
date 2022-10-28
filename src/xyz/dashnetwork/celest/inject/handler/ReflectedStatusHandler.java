/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.inject.handler;

import io.netty.channel.ChannelHandler;
import xyz.dashnetwork.celest.utils.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ReflectedStatusHandler implements InvocationHandler {

    private final Object original;
    private final ChannelHandler channelHandler;

    public ReflectedStatusHandler(Object original, ChannelHandler channelHandler) {
        this.original = original;
        this.channelHandler = channelHandler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws ReflectiveOperationException {
        String name = method.getName();
        Class<?>[] types = method.getParameterTypes();

        Method originalMethod = original.getClass().getMethod(name, types);

        if (ReflectionUtils.isMethod(originalMethod, "handle", ReflectionUtils.arrayStatusRequest())) {
            System.out.println("request handle invoked");
        }

        return originalMethod.invoke(original, args);
    }

}
