/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.storage.Cache;
import xyz.dashnetwork.celest.utils.storage.Storage;
import xyz.dashnetwork.celest.utils.data.PunishData;
import xyz.dashnetwork.celest.utils.data.UserData;
import xyz.dashnetwork.celest.vault.Vault;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class User {

    private static final Vault vault = Celest.getVault();
    private static final List<User> users = new ArrayList<>();
    private final Player player;
    private final UUID uuid;
    private final String stringUuid;
    private final String stringAddress;
    private Address address;
    private UserData userData;

    public User(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.stringUuid = uuid.toString();
        this.stringAddress = player.getRemoteAddress().getHostString();

        load();

        users.add(this);
    }

    public static List<User> getUsers() { return users; }

    public static User getUser(Player player) {
        for (User user : users)
            if (user.uuid.equals(player.getUniqueId()))
                return user;
        return new User(player);
    }

    private void load() {
        userData = Storage.read(stringUuid, Storage.Directory.USER, UserData.class);
        address = new Address(stringAddress, true);

        UUID uniqueId = player.getUniqueId();
        String username = player.getUsername();

        if (userData == null)
            userData = new UserData();
        else {
            String old = userData.getAddress();

            if (!old.equals(stringAddress))
                Address.getAddress(old).removeUserIfPresent(uniqueId);
        }

        userData.setAddress(stringAddress);
        userData.setUsername(username);
        address.addUserIfNotPresent(uuid, username);

        Cache.generate(uuid, userData);
    }

    public void save(boolean saveAddress) {
        Storage.write(stringUuid, Storage.Directory.USER, userData);

        if (saveAddress)
            address.save();
    }

    public void remove() {
        users.remove(this);
        address.remove();
    }

    public void setData(UserData userData) { this.userData = userData; }

    public Player getPlayer() { return player; }

    public UserData getData() { return userData; }

    public Address getAddress() { return address; }

    public boolean isStaff() { return player.hasPermission("dashnetwork.staff") || isAdmin(); }

    public boolean isAdmin() { return player.hasPermission("dashnetwork.admin") || isOwner(); }

    public boolean isOwner() { return player.hasPermission("dashnetwork.owner") || isDash() || isKevin() || isGolden(); }

    public boolean isDash() { return stringUuid.equals("4f771152-ce61-4d6f-9541-1d2d9e725d0e"); }

    public boolean isKevin() { return stringUuid.equals("a948c50c-ede2-4dfa-9b6c-688daf22197c"); }

    public boolean isGolden() { return stringUuid.equals("bbeb983a-3111-4722-bcf0-e6aafbd5f7d2"); }

    public PunishData getBan() {
        PunishData fromUserData = userData.getBan();

        if (fromUserData != null)
            return fromUserData;

        return address.getData().getBan();
    }

    public PunishData getMute() {
        PunishData fromUserData = userData.getMute();

        if (fromUserData != null)
            return fromUserData;

        return address.getData().getMute();
    }

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

}
