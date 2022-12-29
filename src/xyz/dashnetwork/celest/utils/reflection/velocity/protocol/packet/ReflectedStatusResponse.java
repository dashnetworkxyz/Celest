/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet;

import xyz.dashnetwork.celest.utils.reflection.ClassList;

import java.lang.reflect.Constructor;

public final class ReflectedStatusResponse {

    private static final Class<?> clazz;
    private static Constructor<?> constructor;
    private final Object original;

    static {
        clazz = ClassList.STATUS_RESPONSE;

        try {
            constructor = clazz.getConstructor(CharSequence.class);
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }
    }

    public ReflectedStatusResponse(CharSequence sequence) throws ReflectiveOperationException {
        original = constructor.newInstance(sequence);
    }

    public Object original() { return original; }

}
