/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.dashnetwork.celest.Celest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class Storage {

    private static final Gson gson = new GsonBuilder().create();
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
        new File(directory.getFile(), fileName + ".json.gz").delete();
    }

    public static void write(String fileName, Directory directory, Object object) {
        File file = new File(directory.getFile(), fileName + ".json.gz");
        String json = gson.toJson(object);

        try {
            if (!file.exists())
                file.createNewFile();

            // GZIP because we have more CPU power than SSD power.
            GZIPOutputStream stream = new GZIPOutputStream(new FileOutputStream(file));
            OutputStreamWriter writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
            writer.write(json);
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static <T> T read(String fileName, Directory directory, Class<T> clazz) {
        File file = new File(directory.getFile(), fileName + ".json.gz");

        if (!file.exists())
            return null;

        return gson.fromJson(new String(readFile(file), StandardCharsets.UTF_8), clazz);
    }

    public static <T> List<T> readAll(Directory directory, Class<T> clazz) {
        File[] files = directory.getFile().listFiles();

        if (files == null || files.length == 0)
            return Collections.emptyList();

        List<T> list = new ArrayList<>();

        for (File file : files)
            list.add(gson.fromJson(new String(readFile(file), StandardCharsets.UTF_8), clazz));

        return list;
    }

    private static byte[] readFile(File file) {
        byte[] data;

        try {
            GZIPInputStream stream = new GZIPInputStream(new FileInputStream(file));
            data = stream.readAllBytes();
            stream.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        return data;
    }

}
