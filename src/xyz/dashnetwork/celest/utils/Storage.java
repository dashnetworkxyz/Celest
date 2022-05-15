/*
 * Copyright (c) 2022. Andrew Bell.
 * All rights reserved.
 */

package xyz.dashnetwork.celest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.dashnetwork.celest.Celest;

import java.io.*;
import java.nio.file.Path;

public class Storage {

    public enum Directory { USERDATA, CACHE }

    private static final Gson gson;
    private static final File folder;

    static {
        gson = new GsonBuilder().create();
        folder = new File(Path.of("plugins", "Celest").toUri());

        for (Directory directory : Directory.values()) {
            String name = directory.name().toLowerCase();
            File file = new File(folder, name);

            if (!file.exists() && file.mkdirs())
                throw new RuntimeException("Failed to create " + name + " folder");
        }
    }

    public static void write(String fileName, Directory directory, Object object) {
        File file = new File(new File(folder, directory.name().toLowerCase()), fileName + ".json");
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
        File file = new File(new File(folder, directory.name().toLowerCase()), fileName + ".json");

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
