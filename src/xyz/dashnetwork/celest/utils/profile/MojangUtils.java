/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.profile;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.limbo.Limbo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public final class MojangUtils {

    private static final Logger logger = Celest.getLogger();
    private static final Gson gson = Celest.getGson();

    public static PlayerProfile fromUsername(@NotNull String username) {
        if (username.length() > 16)
            return null;

        Limbo<PlayerProfile> limbo = Limbo.get(PlayerProfile.class, each -> each.getUsername().equalsIgnoreCase(username));

        if (limbo != null) {
            limbo.reset();

            return limbo.getObject();
        }

        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            Response response = gson.fromJson(new InputStreamReader(url.openStream()), Response.class);

            if (response == null)
                return null;

            PlayerProfile profile = response.toProfile();
            new Limbo<>(profile);

            return profile;
        } catch (IOException exception) {
            logger.warn("Failed to pull response from Mojang API.");
            exception.printStackTrace();
            return null;
        }
    }

    public static PlayerProfile fromUuid(@NotNull UUID uuid) {
        Limbo<PlayerProfile> limbo = Limbo.get(PlayerProfile.class, each -> each.getUuid().equals(uuid));

        if (limbo != null) {
            limbo.reset();

            return limbo.getObject();
        }

        try {
            URL url = new URL("https://api.mojang.com/user/profile/" + uuid);
            Response response = gson.fromJson(new InputStreamReader(url.openStream()), Response.class);

            if (response == null)
                return null;

            PlayerProfile profile = response.toProfile();
            new Limbo<>(profile);

            return profile;
        } catch (IOException exception) {
            logger.warn("Failed to pull response from Mojang API.");
            exception.printStackTrace();
            return null;
        }
    }

    private static class Response {

        private String name, id;

        public PlayerProfile toProfile() {
            return new PlayerProfile(
                    UUID.fromString(id.replaceFirst(
                            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                            "$1-$2-$3-$4-$5")),
                    name);
        }

    }

}
