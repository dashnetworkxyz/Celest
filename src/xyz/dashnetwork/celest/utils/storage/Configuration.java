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

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import xyz.dashnetwork.celest.Celest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.function.IntFunction;

public final class Configuration {

    private static final URL resource = Configuration.class.getClassLoader().getResource("config.yml");
    private static final File file = new File(Celest.getDirectory().toFile(), "config.yml");
    private static final ConfigurationOptions options = ConfigurationOptions.defaults().withShouldCopyDefaults(true);
    private static ConfigurationNode config;

    public static void load() {
        assert resource != null;

        if (!file.exists()) {
            try {
                Files.copy(resource.openStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        YAMLConfigurationLoader.Builder builder = YAMLConfigurationLoader.builder();
        builder.setDefaultOptions(options);
        builder.setFile(file);

        ConfigurationLoader<ConfigurationNode> loader = builder.build();

        try {
            config = loader.load(options);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> type, String node) { return (T) config.getNode(node).getValue(type); }

    @SuppressWarnings("unchecked")
    public static <T> T[] get(IntFunction<T[]> function, String node) {
        Class<?> type = function.apply(0).getClass();

        return ((ArrayList<T>) config.getNode(node).getValue(type)).toArray(function);
    }

}
