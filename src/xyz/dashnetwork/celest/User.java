/*
 * Copyright (c) 2022. Andrew Bell.
 * All rights reserved.
 */

package xyz.dashnetwork.celest;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.UserData;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static List<User> users = new ArrayList<>();
    private Player player;
    private UserData userData;

    public User(Player player) {
        this.player = player;

        load();

        users.add(this);
    }

    public static List<User> getUsers() { return users; }

    public static User getUser(Player player) {
        for (User user : users)
            if (user.getPlayer().getUniqueId().equals(player.getUniqueId()))
                return user;
        return null;
    }

    private void load() {
        // TODO: Load data from files.
    }

    public void save() {
        // TODO: Save data to files.
    }

    public void remove() { users.remove(this); }

    public boolean isBuilder() { return player.hasPermission("dashnetwork.builder") || isOwner(); }

    public boolean isStaff() { return player.hasPermission("dashnetwork.staff") || isAdmin(); }

    public boolean isAdmin() { return player.hasPermission("dashnetwork.admin") || isOwner(); }

    public boolean isOwner() { return player.hasPermission("dashnetwork.owner") || isDash(); }

    public boolean isDash() { return player.getUniqueId().toString().equals("4f771152-ce61-4d6f-9541-1d2d9e725d0e"); }

    public Player getPlayer() { return player; }

    public UserData getData() { return userData; }

}
