/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.storage.Cache;
import xyz.dashnetwork.celest.storage.Storage;
import xyz.dashnetwork.celest.utils.AddressData;
import xyz.dashnetwork.celest.utils.UserData;
import xyz.dashnetwork.celest.vault.Vault;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static final Vault vault = Celest.getVault();
    private static final List<User> users = new ArrayList<>();
    private final Player player;
    private final String uuid;
    private Address address; // TODO
    private UserData userData;

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
            userData = new UserData(player.getRemoteAddress().getHostString(), player.getUsername());

        // TODO: Check for and remove old address entries

        Cache.generate(player);
    }

    public void save() { Storage.write(uuid, Storage.Directory.USERDATA, userData); }

    public void remove() { users.remove(this); }

    public Player getPlayer() { return player; }

    public UserData getData() { return userData; }

    public boolean isStaff() { return player.hasPermission("dashnetwork.staff") || isAdmin(); }

    public boolean isAdmin() { return player.hasPermission("dashnetwork.admin") || isOwner(); }

    public boolean isOwner() { return player.hasPermission("dashnetwork.owner") || isDash() || isKevin(); }

    public boolean isDash() { return uuid.equals("4f771152-ce61-4d6f-9541-1d2d9e725d0e"); }

    public boolean isKevin() { return uuid.equals("a948c50c-ede2-4dfa-9b6c-688daf22197c"); }

    public String getDisplayname() {
        String name = userData.getNickname();
        String prefix = vault.getPrefix(player);
        String suffix = vault.getSuffix(player);

        if (name == null)
            name = player.getUsername();

        if (!prefix.isBlank())
            prefix += " ";
        if (!suffix.isBlank())
            suffix = " " + suffix;

        return prefix + name + suffix;
    }

    // Methods for predicate cleanness
    public boolean isStaffOrVanished() { return isStaff() || userData.getVanish(); }

    public boolean isStaffOrStaffchat() { return isStaff() || userData.getStaffChat(); }

    public boolean isAdminOrAdminchat() { return isAdmin() || userData.getAdminChat(); }

    public boolean isOwnerOrOwnerchat() { return isOwner() || userData.getOwnerChat(); }

    public boolean isOwnerOrLocalchat() { return isOwner() || userData.getLocalChat(); }

}
