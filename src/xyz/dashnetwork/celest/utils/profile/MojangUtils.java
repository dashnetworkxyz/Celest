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

package xyz.dashnetwork.celest.utils.profile;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.limbo.Limbo;
import xyz.dashnetwork.celest.utils.log.LogType;
import xyz.dashnetwork.celest.utils.log.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public final class MojangUtils {

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
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            String json = new String(url.openStream().readAllBytes());

            if (json.contains("/profile/"))
                return null;

            Response response = gson.fromJson(json, Response.class);

            if (response == null)
                return null;

            PlayerProfile profile = response.toPlayerProfile();
            new Limbo<>(profile);

            return profile;
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
            URL url = new URL("https://api.mojang.com/user/profile/" + uuid);
            String json = new String(url.openStream().readAllBytes());

            if (json.contains("/profile/"))
                return null;

            Response response = gson.fromJson(json, Response.class);

            if (response == null)
                return null;

            PlayerProfile profile = response.toPlayerProfile();
            new Limbo<>(profile);

            return profile;
        } catch (IOException exception) {
            Logger.log(LogType.WARN, true, "Failed to pull response from Mojang API.");
            return null;
        }
    }

    private static class Response {

        private String name, id, error;

        public PlayerProfile toPlayerProfile() {
            return new PlayerProfile(
                    UUID.fromString(id.replaceFirst(
                            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                            "$1-$2-$3-$4-$5")),
                    name);
        }

    }

}
