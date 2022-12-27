/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.connection;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.utils.NamedSource;
import xyz.dashnetwork.celest.utils.OfflineUser;
import xyz.dashnetwork.celest.utils.TimeType;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.utils.chat.ColorUtils;
import xyz.dashnetwork.celest.utils.limbo.Limbo;
import xyz.dashnetwork.celest.utils.storage.Cache;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;
import xyz.dashnetwork.celest.vault.Vault;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class User extends OfflineUser implements NamedSource {

    private static final Map<UUID, User> users = new HashMap<>();
    private static final Vault vault = Celest.getVault();
    private Player player;
    private Address address;
    private String prefix, suffix, nickname;
    private long vaultUpdateTime;

    private User(Player player) {
        super(player.getUsername(), player.getUniqueId());

        this.player = player;
        this.address = Address.getAddress(player.getRemoteAddress().getHostString(), false);
        this.vaultUpdateTime = -1;

        String old = userData.getAddress();

        if (old != null && !old.equals(address.getString()))
            Address.getAddress(old, true).removeUserIfPresent(uuid);

        load();
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
            user.load();

            return user;
        }

        return new User(player);
    }

    private void load() {
        String stringAddress = player.getRemoteAddress().getHostString();
        address = Address.getAddress(stringAddress, false);

        UUID uniqueId = player.getUniqueId();
        String username = getUsername();

        userData.setAddress(stringAddress);
        userData.setUsername(username);
        address.addUserIfNotPresent(uuid, username);

        Cache.generate(uuid, userData);

        users.put(uniqueId, this);
    }

    public void updateDisplayname() {
        String oldPrefix = prefix;
        String oldSuffix = suffix;
        String oldNickname = nickname;

        if (oldPrefix == null)
            oldPrefix = "";

        if (oldSuffix == null)
            oldSuffix = "";

        if (oldNickname == null)
            oldNickname = "";

        nickname = userData.getNickName();

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

        if (!oldPrefix.equals(prefix) || !oldSuffix.equals(suffix) || !oldNickname.equals(nickname))
            Channel.callOut("displayname", this);
    }

    public void remove() {
        users.remove(uuid);

        new Limbo<>(this);
        new Limbo<>(address);
    }

    public boolean canSee(User user) { return isStaff() || !user.getData().getVanish() || getData().getVanish(); }

    public boolean showAddress() { return isAdmin() && !getData().getHideAddress(); }

    public Player getPlayer() { return player; }

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

    @Override
    public String getDisplayname() {
        updateDisplayname();

        return prefix + nickname + suffix;
    }

}
