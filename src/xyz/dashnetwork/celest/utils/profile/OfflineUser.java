/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils.profile;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.limbo.Limbo;
import xyz.dashnetwork.celest.utils.limbo.Savable;
import xyz.dashnetwork.celest.utils.storage.Storage;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.Optional;
import java.util.UUID;

public class OfflineUser implements Savable {

    protected final UUID uuid;
    protected final String stringUuid;
    protected String username;
    protected UserData userData;
    private final boolean generated;

    protected OfflineUser(UUID uuid, String username, boolean shouldLimbo) {
        this.uuid = uuid;
        this.stringUuid = uuid.toString();
        this.username = username;

        userData = null;

        if (!shouldLimbo) {
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

        if (shouldLimbo)
            new Limbo<>(this);
    }

    public static OfflineUser getOfflineUser(PlayerProfile profile) {
        UUID uuid = profile.uuid();
        Optional<Player> optional = Celest.getServer().getPlayer(uuid);

        if (optional.isPresent())
            return User.getUser(optional.get());

        Limbo<OfflineUser> limbo = Limbo.get(OfflineUser.class, each -> each.uuid.equals(uuid));

        if (limbo != null)
            return limbo.getObject();

        return new OfflineUser(uuid, profile.username(), true);
    }

    public UUID getUuid() { return uuid; }

    public String getUsername() { return username; }

    public PlayerProfile toPlayerProfile() { return new PlayerProfile(uuid, username); }

    @Override
    public void save() {
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

}
