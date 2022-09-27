/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.Celest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class MojangUtils {

    private static final Gson gson = new GsonBuilder().create();

    public static PlayerProfile fromUsername(@NotNull String username) {
        if (username.length() > 16)
            return null;

        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            Response response = gson.fromJson(new InputStreamReader(url.openStream()), Response.class);

            if (response == null)
                return null;

            return response.toProfile();
        } catch (IOException exception) {
            Celest.getLogger().warn("Failed to pull response from Mojang API.");
            exception.printStackTrace();
            return null;
        }
    }

    public static PlayerProfile fromUuid(@NotNull UUID uuid) {
        try {
            URL url = new URL("https://api.mojang.com/user/profile/" + uuid);
            Response response = gson.fromJson(new InputStreamReader(url.openStream()), Response.class);

            if (response == null)
                return null;

            return response.toProfile();
        } catch (IOException exception) {
            Celest.getLogger().warn("Failed to pull response from Mojang API.");
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
