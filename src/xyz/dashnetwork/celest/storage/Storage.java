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

package xyz.dashnetwork.celest.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.log.LogType;
import xyz.dashnetwork.celest.log.Logger;
import xyz.dashnetwork.celest.storage.data.UserData;
import xyz.dashnetwork.celest.storage.data.serializer.UserDataSerializer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Storage {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(UserData.class, new UserDataSerializer())
            .create();
    private static final File folder = new File(Celest.getDirectory().toFile(), "data");

    public enum Directory {

        PARENT(folder),
        ADDRESS(new File(folder, "address")),
        LOOKUP(new File(folder, "lookup")),
        USER(new File(folder, "user"));

        private final File file;

        Directory(File file) { this.file = file; }

        public File getFile() { return file; }

    }

    public static void mkdir() {
        for (Directory directory : Directory.values()) {
            File file = directory.getFile();

            if (!file.exists() && !file.mkdirs())
                Logger.log(LogType.ERROR, false,
                        "Failed to create folder: " + file.getAbsolutePath()
                );
        }
    }

    public static void delete(String fileName, Directory directory) {
        if (!new File(directory.getFile(), fileName + ".json").delete())
            Logger.log(LogType.WARN, true,
                    "Failed to delete file for " + fileName + ".json (" + directory.name() + ")"
            );
    }

    public static void write(String fileName, Directory directory, Object object) {
        File file = new File(directory.getFile(), fileName + ".json");
        String json = gson.toJson(object);

        Writer writer = null;

        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            writer.write(json);
        } catch (IOException exception) {
            Logger.throwable(exception);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException exception) {
                    Logger.throwable(exception);
                }
            }
        }
    }

    public static <T> T read(String fileName, Directory directory, Class<T> clazz) {
        File file = new File(directory.getFile(), fileName + ".json");

        if (!file.exists())
            return null;

        return readFile(file, clazz);
    }

    public static <T> Map<String, T> readAll(Directory directory, Class<T> clazz) {
        File[] files = directory.getFile().listFiles();

        if (files == null || files.length == 0)
            return Collections.emptyMap();

        Map<String, T> map = new HashMap<>();

        for (File file : files)
            map.put(file.getName().split("\\.json")[0], readFile(file, clazz));

        return map;
    }

    private static <T> T readFile(File file, Class<T> clazz) {
        InputStream input = null;

        try {
            input = new FileInputStream(file);

            return gson.fromJson(new String(input.readAllBytes(), StandardCharsets.UTF_8), clazz);
        } catch (IOException exception) {
            Logger.throwable(exception);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException exception) {
                    Logger.throwable(exception);
                }
            }
        }
    }

}
