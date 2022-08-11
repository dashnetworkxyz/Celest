/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import xyz.dashnetwork.celest.Celest;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Configuration {

    // TODO: switch to typesafe config. this one sucks.

    private static final URL resource = Configuration.class.getClassLoader().getResource("config.yml");
    private static final File file = new File(Celest.getDirectory().toFile(), "config.yml");
    private static final ConfigurationOptions options = ConfigurationOptions.defaults().withShouldCopyDefaults(true);
    private static ConfigurationLoader<ConfigurationNode> loader;
    private static ConfigurationNode config;

    public static void load() {
        assert resource != null;

        YAMLConfigurationLoader.Builder builder = YAMLConfigurationLoader.builder();
        builder.setFile(file);
        builder.setURL(resource);
        builder.setDefaultOptions(options);

        loader = builder.build();

        try {
            config = loader.load(options);
            loader.save(config);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static ConfigurationNode getNode(String node) { return config.getNode(node); }

    public static <T>T get(Class<T> type, String node) {
        return (T) config.getNode(node).getValue(type); // TODO: test if this actually works
    }

}
