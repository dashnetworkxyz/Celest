/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.Cache;
import xyz.dashnetwork.celest.utils.Storage;
import xyz.dashnetwork.celest.utils.UserData;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static final List<User> users = new ArrayList<>();
    private final Player player;
    private UserData userData;
    private String uuid;

    public User(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId().toString();

        load();

        users.add(this);
    }

    public static List<User> getUsers() { return users; }

    public static User getUser(Player player) {
        for (User user : users)
            if (user.player.getUniqueId().equals(player.getUniqueId()))
                return user;
        return new User(player);
    }

    private void load() {
        userData = Storage.read(uuid, Storage.Directory.USERDATA, UserData.class);

        if (userData == null)
            userData = new UserData();

        Cache.generate(userData);
    }

    public void save() { Storage.write(uuid, Storage.Directory.USERDATA, userData); }

    public void remove() { users.remove(this); }

    public boolean isBuilder() { return player.hasPermission("dashnetwork.builder") || isOwner(); }

    public boolean isStaff() { return player.hasPermission("dashnetwork.staff") || isAdmin(); }

    public boolean isAdmin() { return player.hasPermission("dashnetwork.admin") || isOwner(); }

    public boolean isOwner() { return player.hasPermission("dashnetwork.owner") || isDash(); }

    public boolean isDash() { return uuid.equals("4f771152-ce61-4d6f-9541-1d2d9e725d0e"); }

    public Player getPlayer() { return player; }

    public UserData getData() { return userData; }

}
