/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet;

import xyz.dashnetwork.celest.utils.reflection.ClassList;

import java.lang.reflect.Constructor;

public class ReflectedStatusResponse {

    private static final Class<?> clazz;
    private static final Constructor<?> constructor;
    private final Object original;

    static {
        try {
            clazz = ClassList.STATUS_RESPONSE;

            constructor = clazz.getConstructor(CharSequence.class);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ReflectedStatusResponse(CharSequence sequence) throws ReflectiveOperationException {
        original = constructor.newInstance(sequence);
    }

    public Object original() { return original; }

}
