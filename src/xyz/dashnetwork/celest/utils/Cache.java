/*
 * Copyright (c) 2022. Andrew Bell.
 * All rights reserved.
 */

package xyz.dashnetwork.celest.utils;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public class Cache {

    private static File userdata = new File(Path.of("plugins", "Celest", "userdata").toUri());
    private static Address address;
    private static Username username;
    // TODO: Punish system

    public static void load() {
        address = Storage.read("address", Storage.Directory.CACHE, Address.class);
        username = Storage.read("username", Storage.Directory.CACHE, Username.class);

        if (address == null || username == null) {
            address = new Address();
            username = new Username();

            address.test = "test";
            username.test = UUID.randomUUID();
        }
    }

    public static void save() {
        Storage.write("address", Storage.Directory.CACHE, address);
        Storage.write("username", Storage.Directory.CACHE, username);
    }

    public static void generate(UserData data) {

    }

    public static class Address {

        public String test;

    }

    public static class Username {

        public UUID test;

    }

}
