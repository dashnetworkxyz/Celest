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

package xyz.dashnetwork.celest.connection;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.storage.data.UserData;
import xyz.dashnetwork.celest.util.PermissionType;
import xyz.dashnetwork.celest.util.TimeType;
import xyz.dashnetwork.celest.util.TimeUtil;
import xyz.dashnetwork.celest.chat.ColorUtil;
import xyz.dashnetwork.celest.chat.builder.PageBuilder;
import xyz.dashnetwork.celest.storage.data.PunishData;
import xyz.dashnetwork.celest.vault.Vault;

public final class User implements CommandSource {

    private static final Vault vault = Celest.getVault();
    private final Player player;
    private UserData userData;
    private PageBuilder pageBuilder;
    private String prefix, suffix, nickname;
    private long vaultUpdateTime;

    public User(Player player) {
        this.player = player;
        this.pageBuilder = null;
        this.vaultUpdateTime = -1;

        String old = userData.getAddress();

        if (!disableSave && old != null && !old.equals(address.getString())) {
            Address.getAddress(old, true).removeUserIfPresent(uuid);

            userData.setAuthenticated(false);
        }

        if (!disableSave)
            load();

        updateDisplayname();

        users.put(uuid, this);
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

    public void updateDisplayName() {
        String oldPrefix = prefix == null ? "" : prefix;
        String oldSuffix = suffix == null ? "" : suffix;
        String oldNickname = nickname == null ? "" : nickname;

        nickname = userData.getNickName();

        if (nickname == null)
            nickname = getUsername();

        if (!TimeUtil.isRecent(vaultUpdateTime, TimeType.SECOND.toMillis(5))) {
            prefix = ColorUtil.fromAmpersand(vault.getPrefix(player));
            suffix = ColorUtil.fromAmpersand(vault.getSuffix(player));

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
        // TODO: save userdata
        users.remove(player.getUniqueId());
    }

    public boolean canSee(User user) { return PermissionType.STAFF.hasPermission(this) || !user.getData().getVanish() || getData().getVanish(); }

    public boolean showAddress() { return isAdmin() && !getData().getHideAddress(); }

    public Player getPlayer() { return player; }

    public Address getAddress() { return address; }

    public PageBuilder getPageBuilder() { return pageBuilder; }

    public void setPageBuilder(PageBuilder pageBuilder) { this.pageBuilder = pageBuilder; }

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

    public String getDisplayName() {
        updateDisplayName();

        return prefix + nickname + suffix;
    }

    @Override
    public void sendMessage(@NotNull Component component) { player.sendMessage(component); }

    @Override
    public Tristate getPermissionValue(String value) { return player.getPermissionValue(value); }

}
