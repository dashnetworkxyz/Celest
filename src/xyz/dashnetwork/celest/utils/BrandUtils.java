/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

public final class BrandUtils {

    public static byte[] toBytes(String name) {
        ByteBuf buf = Unpooled.buffer();
        byte[] array = name.getBytes(StandardCharsets.UTF_8);

        writeVarInt(buf, array.length);
        buf.writeBytes(array);

        return buf.array();
    }

    private static void writeVarInt(ByteBuf buf, int value) {
        int part;

        do {
            part = value & 0x7F;
            value >>>= 7;

            if (value != 0)
                part |= 0x80;

            buf.writeByte(part);
        } while (value != 0);
    }

}
