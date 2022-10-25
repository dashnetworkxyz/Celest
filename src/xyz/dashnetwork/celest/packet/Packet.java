/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.packet;

import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class Packet {

    public abstract void read(ByteBuf buf);

    public abstract byte[] write();

    // TODO

    private static final Map<Integer, Supplier<Packet>> packets = new HashMap<>();

    public static void register(int id, @NotNull Supplier<Packet> supplier) { packets.put(id, supplier); }

    public static Packet get(int id) {
        Supplier<Packet> supplier = packets.get(id);

        if (supplier == null)
            return null;

        return supplier.get();
    }

}
