/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.storage;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import xyz.dashnetwork.celest.Celest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public class Configuration {

    private static final URL resource = Configuration.class.getClassLoader().getResource("config.yml");
    private static final File file = new File(Celest.getDirectory().toFile(), "config.yml");
    private static final ConfigurationOptions options = ConfigurationOptions.defaults().withShouldCopyDefaults(true);
    private static ConfigurationNode config;

    public static void load() {
        assert resource != null;

        YAMLConfigurationLoader.Builder builder = YAMLConfigurationLoader.builder();
        builder.setFile(file);
        builder.setURL(resource);
        builder.setDefaultOptions(options);

        ConfigurationLoader<ConfigurationNode> loader = builder.build();

        try {
            config = loader.load(options);
            loader.save(config);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static ConfigurationNode getNode(String node) { return config.getNode(node); }

    @SuppressWarnings("unchecked")
    public static <T>T get(Class<T> type, String node) { return (T) config.getNode(node).getValue(type); }

    @SuppressWarnings("unchecked")
    public static <T>T[] getArray(IntFunction<T[]> function, String node) {
        Class<?> type = function.apply(0).getClass();

        return ((ArrayList<T>) config.getNode(node).getValue(type)).toArray(function);
    }

}
