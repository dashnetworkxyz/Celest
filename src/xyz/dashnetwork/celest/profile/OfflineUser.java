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

package xyz.dashnetwork.celest.profile;

import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.util.GameProfile;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.limbo.Limbo;
import xyz.dashnetwork.celest.limbo.Savable;
import xyz.dashnetwork.celest.storage.Storage;
import xyz.dashnetwork.celest.storage.data.UserData;

import java.util.Optional;
import java.util.UUID;

public class OfflineUser implements Savable {

    protected final UUID uuid;
    protected final String stringUuid;
    protected String username;
    protected UserData userData;
    protected GameProfile realJoin;
    protected boolean disableSave;
    private final boolean generated;

    protected OfflineUser(UUID uuid, String username, boolean shouldLimbo) {
        this.uuid = uuid;
        this.stringUuid = uuid.toString();
        this.username = username;
        this.userData = null;
        this.realJoin = null;
        this.disableSave = false;

        if (shouldLimbo)
            new Limbo<>(this);
        else {
            Limbo<OfflineUser> limbo = Limbo.get(OfflineUser.class, each -> each.uuid.equals(uuid));

            if (limbo != null) {
                limbo.cancel();

                userData = limbo.getObject().getData();
            }
        }

        if (userData == null)
            userData = Storage.read(stringUuid, Storage.Directory.USER, UserData.class);

        if (userData == null) {
            userData = new UserData(username);
            generated = true;
        } else
            generated = false;
    }

    public static OfflineUser getOfflineUser(GameProfile profile) {
        UUID uuid = profile.getId();
        Optional<Player> optional = Celest.getServer().getPlayer(uuid);

        if (optional.isPresent())
            return User.getUser(optional.get());

        Limbo<OfflineUser> limbo = Limbo.get(OfflineUser.class, each -> each.uuid.equals(uuid));

        if (limbo != null)
            return limbo.getObject();

        return new OfflineUser(uuid, profile.getName(), true);
    }

    public UUID getUuid() { return uuid; }

    public String getUsername() { return username; }

    public GameProfile toGameProfile() { return new GameProfile(uuid, username, ImmutableList.of()); }

    @Override
    public void save() {
        if (disableSave)
            return;

        if (userData.isObsolete()) {
            if (!generated)
                Storage.delete(stringUuid, Storage.Directory.USER);
        } else {
            Storage.write(stringUuid, Storage.Directory.USER, userData);
            Storage.write(username.toLowerCase(), Storage.Directory.LOOKUP, uuid);
        }
    }

    public boolean isActive() { return false; }

    public void setData(UserData userData) { this.userData = userData; }

    public UserData getData() { return userData; }

    public GameProfile getRealJoin() { return realJoin; }

    public void setRealJoin(GameProfile profile) { this.realJoin = profile; }

    public void disableSaving() { disableSave = true; }

}
