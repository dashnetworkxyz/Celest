/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public class Cache {

    private static final Gson gson = new GsonBuilder().create();
    private static final File userdata = new File(Path.of("plugins", "Celest", "userdata").toUri());
    private static List<Address> addresses;
    private static List<Username> usernames;
    // TODO: Punish system

    public static void load() {
        Address[] addressArray = Storage.read("address", Storage.Directory.CACHE, Address[].class);
        Username[] usernameArray = Storage.read("username", Storage.Directory.CACHE, Username[].class);

        addresses = new ArrayList<>();
        usernames = new ArrayList<>();

        if (addressArray != null)
            addresses = new ArrayList<>(Arrays.asList(addressArray));

        if (usernameArray != null)
            usernames = new ArrayList<>(Arrays.asList(usernameArray));
    }

    public static void save() {
        Storage.write("address", Storage.Directory.CACHE, addresses.toArray(new Address[0]));
        Storage.write("username", Storage.Directory.CACHE, usernames.toArray(new Username[0]));
    }

    public static void generate(UserData data) {
        // TODO: Update username and address cache on login
    }

    public static class Address {

        private String address;

        public Address(String address) {
            this.address = address;

            // TODO: UUID list
        }

        public String getAddress() { return address; }

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
