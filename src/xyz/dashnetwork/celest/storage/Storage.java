/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.dashnetwork.celest.Celest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static final Gson gson = new GsonBuilder().create();
    private static final File folder = Celest.getDirectory().toFile();

    public enum Directory {

        PARENT(folder),
        ADDRESSDATA(new File(folder, "addressdata")),
        USERDATA(new File(folder, "userdata"));

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
        File file = new File(directory.getFile(), fileName + ".json");

        if (file.delete())
            Celest.getLogger().info("deleted " + fileName + ".json (" + directory.name() + ")");
    }

    public static void write(String fileName, Directory directory, Object object) {
        File file = new File(directory.getFile(), fileName + ".json");
        String json = gson.toJson(object);

        try {
            if (file.createNewFile())
                Celest.getLogger().info("created " + fileName + ".json (" + directory.name() + ")");

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

        return gson.fromJson(new String(readFile(file)), clazz);
    }

    public static <T>List<T> readAll(Directory directory, Class<T> clazz) {
        File[] files = directory.getFile().listFiles();

        if (files == null || files.length == 0)
            return null;

        List<T> list = new ArrayList<>();

        for (File file : files)
            list.add(gson.fromJson(new String(readFile(file)), clazz));

        return list;
    }

    private static byte[] readFile(File file) {
        byte[] data;

        try {
            DataInputStream input = new DataInputStream(new FileInputStream(file));
            data = input.readAllBytes();
            input.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        return data;
    }

}
