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

package xyz.dashnetwork.celest.utils.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.storage.data.UserData;
import xyz.dashnetwork.celest.utils.storage.data.serializer.UserDataSerializer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Storage {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(UserData.class, new UserDataSerializer())
            .create();
    private static final File folder = new File(Celest.getDirectory().toFile(), "data");

    public enum Directory {

        PARENT(folder),
        ADDRESS(new File(folder, "address")),
        USER(new File(folder, "user"));

        private final File file;

        Directory(File file) { this.file = file; }

        public File getFile() { return file; }

    }

    public static void mkdir() {
        for (Directory directory : Directory.values()) {
            File file = directory.getFile();

            if (!file.exists() && !file.mkdirs())
                Celest.getLogger().error("Failed to create " + directory.name() + " folder");
        }
    }

    public static void delete(String fileName, Directory directory) {
        new File(directory.getFile(), fileName + ".json").delete();
    }

    public static void write(String fileName, Directory directory, Object object) {
        File file = new File(directory.getFile(), fileName + ".json");
        String json = gson.toJson(object);

        try {
            if (!file.exists())
                file.createNewFile();

            FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8);
            writer.write(json);
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static <T> T read(String fileName, Directory directory, Class<T> clazz) {
        File file = new File(directory.getFile(), fileName + ".json");

        if (!file.exists())
            return null;

        return readFile(file, clazz);
    }

    public static <T> List<T> readAll(Directory directory, Class<T> clazz) {
        File[] files = directory.getFile().listFiles();

        if (files == null || files.length == 0)
            return Collections.emptyList();

        List<T> list = new ArrayList<>();

        for (File file : files)
            list.add(readFile(file, clazz));

        return list;
    }

    private static <T> T readFile(File file, Class<T> clazz) {
        byte[] data;

        try {
            InputStream stream = new FileInputStream(file);
            data = stream.readAllBytes();
            stream.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        return gson.fromJson(new String(data, StandardCharsets.UTF_8), clazz);
    }

}
