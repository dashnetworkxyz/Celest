/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.dashnetwork.celest.Celest;

import java.io.*;
import java.nio.file.Path;

public class Storage {

    private static final Gson gson = new GsonBuilder().create();
    private static final File folder = Path.of("plugins", "Celest").toFile();

    public enum Directory {

        USERDATA(new File(folder, "userdata")),
        CACHE(new File(folder, "cache"));

        private File file;

        Directory(File file) { this.file = file; }

        public File getFile() { return file; }

    }

    public static void mkdir() {
        for (Directory directory : Directory.values()) {
            File file = directory.getFile();

            if (!file.exists() && !file.mkdirs())
                throw new RuntimeException("Failed to create " + directory.name() + " folder");
        }
    }

    public static void write(String fileName, Directory directory, Object object) {
        File file = new File(directory.getFile(), fileName + ".json");
        String json = gson.toJson(object);

        try {
            if (file.createNewFile())
                Celest.getLogger().info(fileName + ".json generated");

            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static <T>T read(String fileName, Directory directory, Class<T> clazz) {
        File file = new File(directory.getFile(), fileName + ".json");

        if (!file.exists())
            return null;

        byte[] data;

        try {
            DataInputStream input = new DataInputStream(new FileInputStream(file));
            data = input.readAllBytes();
            input.close();
        } catch (IOException exception) {
            exception.printStackTrace();

            return null;
        }

        return gson.fromJson(new String(data), clazz);
    }

}
