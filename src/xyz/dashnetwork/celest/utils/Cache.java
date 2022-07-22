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
    private static List<Username> username;
    // TODO: Punish system

    public static void load() {
        address = Storage.read("address", Storage.Directory.CACHE, Address.class);
        username = Storage.read("username", Storage.Directory.CACHE, List.class);

        if (address == null || username == null) {
            address = new Address();
            username = new ArrayList<>();

            username.add(new Username("test", UUID.randomUUID()));
            username.add(new Username("testy 2", UUID.randomUUID()));

            address.test = "test";
        }
    }

    public static void save() {
        Storage.write("address", Storage.Directory.CACHE, address);
        Storage.write("username", Storage.Directory.CACHE, username);
    }

    public static void generate(UserData data) {
        // TODO: Update username and address cache on login
    }

    public static class Address {

        public String test;

    }

    public static class Username {

        private String username;
        private UUID uuid;

        public Username(String username, UUID uuid) {
            this.username = username;
            this.uuid = uuid;
        }

        public String getUsername() { return username; }

        public UUID getUUID() { return uuid; }

    }

}
