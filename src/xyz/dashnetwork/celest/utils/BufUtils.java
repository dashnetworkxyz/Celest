/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class BufUtils {

    public static int readVarInt(ByteBuf buffer) {
        int numberRead = 0;
        int result = 0;
        byte read;

        do {
            read = buffer.readByte();
            int value = (read & 0b01111111);

            result |= (value << (7 * numberRead));
            numberRead++;

            if (numberRead > 5)
                throw new RuntimeException("VarInt is too big");
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static String readString(ByteBuf buffer) {
        int size = readVarInt(buffer);
        byte[] data = new byte[size];

        buffer.readBytes(data);

        return new String(data, StandardCharsets.UTF_8);
    }

}
