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

import com.google.gson.Gson;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.log.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class GithubUtils {

    private record Response(String status, int behind_by) {}

    private static final Gson gson = Celest.getGson();

    public static int getGitDistance(String repository, String branch, String hash) {
        try {
            HttpURLConnection connection = (HttpURLConnection)
                    new URL("https://api.github.com/repos/" + repository + "/compare/" + branch + "..." + hash).openConnection();
            connection.connect();

            if (connection.getResponseCode() != 200)
                return -1;

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            Response response = gson.fromJson(reader, Response.class);

            if (response.status().equals("behind"))
                return response.behind_by();

            return 0;
        } catch (IOException exception) {
            Logger.throwable(exception);
        }

        return -1;
    }

}
