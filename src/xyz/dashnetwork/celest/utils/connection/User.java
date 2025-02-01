/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils.connection;

import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.utils.TimeType;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.utils.chat.ColorUtils;
import xyz.dashnetwork.celest.utils.chat.builder.PageBuilder;
import xyz.dashnetwork.celest.utils.limbo.Limbo;
import xyz.dashnetwork.celest.utils.profile.NamedSource;
import xyz.dashnetwork.celest.utils.profile.OfflineUser;
import xyz.dashnetwork.celest.utils.storage.Cache;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;
import xyz.dashnetwork.celest.vault.Vault;

import java.util.*;

public final class User extends OfflineUser implements NamedSource, Audience {

    private static final Map<UUID, User> users = new HashMap<>();
    private static final Vault vault = Celest.getVault();
    private Player player;
    private Address address;
    private PageBuilder pageBuilder;
    private String prefix, suffix, nickname;
    private long vaultUpdateTime;

    private User(Player player) {
        super(player.getUniqueId(), player.getUsername(), false);

        this.player = player;
        this.address = Address.getAddress(player.getRemoteAddress().getHostString(), false);
        this.pageBuilder = null;
        this.vaultUpdateTime = -1;

        String old = userData.getAddress();

        if (old != null && !old.equals(address.getString())) {
            Address.getAddress(old, true).removeUserIfPresent(uuid);

            userData.setAuthenticated(false);
        }

        if (!disableSave)
            load();

        updateDisplayname();

        users.put(uuid, this);
    }

    public static Collection<User> getUsers() { return users.values(); }

    public static Optional<User> getUser(Audience audience) {
        if (audience instanceof Player player)
            return Optional.of(getUser(player));

        return Optional.empty();
    }

    public static User getUser(Player player) {
        UUID uuid = player.getUniqueId();

        if (users.containsKey(uuid))
            return users.get(uuid);

        Limbo<User> limbo = Limbo.get(User.class, each -> each.uuid.equals(uuid));

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
        // update username in address
        address.removeUserIfPresent(uuid);
        address.addUserIfNotPresent(uuid, username);

        String stringAddress = player.getRemoteAddress().getHostString();
        address = Address.getAddress(stringAddress, false);

        userData.setAddress(stringAddress);
        userData.setUsername(username);
        address.addUserIfNotPresent(uuid, username);

        Cache.generate(uuid, username, stringAddress);
    }

    public void updateDisplayname() {
        String oldPrefix = prefix == null ? "" : prefix;
        String oldSuffix = suffix == null ? "" : suffix;
        String oldNickname = nickname == null ? "" : nickname;

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

        if (!disableSave) {
            // new Limbo<>(this); temp disable limbo
            new Limbo<>(address);
        }
    }

    public boolean canSee(User user) { return isStaff() || !user.getData().getVanish() || getData().getVanish(); }

    public boolean showAddress() { return isAdmin() && !getData().getHideAddress(); }

    public Player getPlayer() { return player; }

    public Address getAddress() { return address; }

    public PageBuilder getPageBuilder() { return pageBuilder; }

    public void setPageBuilder(PageBuilder pageBuilder) { this.pageBuilder = pageBuilder; }

    public boolean isStaff() { return player.hasPermission("dashnetwork.staff") || isAdmin(); }

    public boolean isAdmin() { return player.hasPermission("dashnetwork.admin") || isOwner(); }

    public boolean isOwner() { return player.hasPermission("dashnetwork.owner") || isDash() || isCryptic() || isGolden(); }

    public boolean isDash() { return stringUuid.equals("4f771152-ce61-4d6f-9541-1d2d9e725d0e"); }

    public boolean isCryptic() { return stringUuid.equals("a948c50c-ede2-4dfa-9b6c-688daf22197c"); }

    public boolean isGolden() { return stringUuid.equals("bbeb983a-3111-4722-bcf0-e6aafbd5f7d2"); }

    public boolean isAuthenticated() { return userData.getTwoFactor() == null || userData.getAuthenticated(); }

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
    public boolean isActive() { return getPlayer().isActive(); }

    @Override
    public String getDisplayname() {
        updateDisplayname();

        return prefix + nickname + suffix;
    }

    @Override
    public void sendMessage(@NotNull Component component) { player.sendMessage(component); }

}
