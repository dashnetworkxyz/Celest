/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

public final class SecureUtils {

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
