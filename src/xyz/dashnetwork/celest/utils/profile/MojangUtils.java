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

package xyz.dashnetwork.celest.utils.profile;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.limbo.Limbo;
import xyz.dashnetwork.celest.utils.log.LogType;
import xyz.dashnetwork.celest.utils.log.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public final class MojangUtils {

    private record Response(String name, String id) {

        public PlayerProfile toPlayerProfile() {
            return new PlayerProfile(
                    UUID.fromString(id.replaceFirst(
                            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                            "$1-$2-$3-$4-$5")),
                    name);
        }

    }

    private static final Gson gson = Celest.getGson();

    public static PlayerProfile fromUsername(@NotNull String username) {
        if (username.length() > 16)
            return null;

        Limbo<PlayerProfile> limbo = Limbo.get(PlayerProfile.class, each -> each.username().equalsIgnoreCase(username));

        if (limbo != null) {
            limbo.reset();

            return limbo.getObject();
        }

        try {
            HttpURLConnection connection = (HttpURLConnection)
                    new URL("https://api.mojang.com/users/profiles/minecraft/" + username).openConnection();
            connection.connect();

            if (connection.getResponseCode() != 200)
                return null;

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            Response response = gson.fromJson(reader, Response.class);

            return response.toPlayerProfile();
        } catch (IOException exception) {
            Logger.log(LogType.WARN, true, "Failed to pull response from Mojang API.");
            return null;
        }
    }

    public static PlayerProfile fromUuid(@NotNull UUID uuid) {
        Limbo<PlayerProfile> limbo = Limbo.get(PlayerProfile.class, each -> each.uuid().equals(uuid));

        if (limbo != null) {
            limbo.reset();

            return limbo.getObject();
        }

        try {
            HttpURLConnection connection = (HttpURLConnection)
                    new URL("https://api.mojang.com/user/profile/" + uuid).openConnection();
            connection.connect();

            if (connection.getResponseCode() != 200)
                return null;

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            Response response = gson.fromJson(reader, Response.class);

            return response.toPlayerProfile();
        } catch (IOException exception) {
            Logger.log(LogType.WARN, true, "Failed to pull response from Mojang API.");
            return null;
        }
    }

}
