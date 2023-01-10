/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils;

import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

public final class SecretUtils {

    private static final Base32 base32 = new Base32();

    public static String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] array = new byte[20];

        random.nextBytes(array);

        return base32.encodeToString(array);
    }

    public static String getTOTP(String secret) {
        String hex = Hex.encodeHexString(base32.decode(secret));

        return TOTP.getOTP(hex);
    }

}
