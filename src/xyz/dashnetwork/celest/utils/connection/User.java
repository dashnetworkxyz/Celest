/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.connection;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.connection.limbo.Savable;
import xyz.dashnetwork.celest.utils.TimeType;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.utils.chat.ColorUtils;
import xyz.dashnetwork.celest.utils.connection.limbo.Limbo;
import xyz.dashnetwork.celest.utils.storage.Cache;
import xyz.dashnetwork.celest.utils.storage.Storage;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;
import xyz.dashnetwork.celest.utils.storage.data.UserData;
import xyz.dashnetwork.celest.vault.Vault;

import java.util.*;

public final class User implements Savable, NamedSource {

    private static final Map<UUID, User> users = new HashMap<>();
    private static final Vault vault = Celest.getVault();
    private final UUID uuid;
    private final String stringUuid;
    private Player player;
    private Address address;
    private UserData userData;
    private String prefix, suffix, nickname;
    private long vaultUpdateTime;

    private User(Player player) {
        this.uuid = player.getUniqueId();
        this.stringUuid = uuid.toString();
        this.player = player;
        this.vaultUpdateTime = -1;

        load(true);
        updateDisplayname();
    }

    public static Collection<User> getUsers() { return users.values(); }

    public static User getUser(Player player) {
        UUID uuid = player.getUniqueId();

        if (users.containsKey(uuid))
            return users.get(uuid);

        Limbo<User> limbo = Limbo.getLimbo(User.class, each -> each.uuid.equals(uuid));

        if (limbo != null) {
            limbo.cancel();

            User user = limbo.getObject();
            user.player = player;
            user.load(false);

            return user;
        }

        return new User(player);
    }

    private void load(boolean readFile) {
        if (readFile)
            userData = Storage.read(stringUuid, Storage.Directory.USER, UserData.class);

        String stringAddress = player.getRemoteAddress().getHostString();
        address = Address.getAddress(stringAddress, false);

        UUID uniqueId = player.getUniqueId();
        String username = getUsername();

        if (userData == null)
            userData = new UserData();
        else {
            String old = userData.getAddress();

            if (!old.equals(stringAddress))
                Address.getAddress(old, true).removeUserIfPresent(uniqueId);
        }

        userData.setAddress(stringAddress);
        userData.setUsername(username);
        address.addUserIfNotPresent(uuid, username);

        Cache.generate(uuid, userData);

        users.put(uniqueId, this);
    }

    private void updateDisplayname() {
        nickname = userData.getNickname();

        if (nickname == null)
            nickname = getUsername();

        if (!TimeUtils.isRecent(vaultUpdateTime, TimeType.SECOND.toMillis(5))) {
            prefix = ColorUtils.fromAmpersand(vault.getPrefix(player));
            suffix = ColorUtils.fromAmpersand(vault.getSuffix(player));

            if (!prefix.isBlank())
                prefix += " ";

            if (!suffix.isBlank())
                suffix = " " + suffix;

            vaultUpdateTime = System.currentTimeMillis();
        }
    }

    @Override
    public void save() { Storage.write(stringUuid, Storage.Directory.USER, userData); }

    public void remove() {
        users.remove(uuid);

        new Limbo<>(this);
        new Limbo<>(address);
    }

    public boolean canSee(User user) { return isStaff() || !user.getData().getVanish() || getData().getVanish(); }

    public void setData(UserData userData) { this.userData = userData; }

    public Player getPlayer() { return player; }

    public UUID getUuid() { return uuid; }

    public Address getAddress() { return address; }

    public UserData getData() { return userData; }

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

    @Override
    public String getDisplayname() {
        Optional<ServerConnection> optional = player.getCurrentServer();
        String oldPrefix = prefix;
        String oldSuffix = suffix;
        String oldNickname = nickname;

        updateDisplayname();

        if ((!oldPrefix.equals(prefix) || !oldSuffix.equals(suffix) || !oldNickname.equals(nickname))
                && optional.isPresent())
            Channel.callOut("displayname", optional.get(), this);

        return prefix + nickname + suffix;
    }

    @Override
    public String getUsername() { return player.getUsername(); }

}
