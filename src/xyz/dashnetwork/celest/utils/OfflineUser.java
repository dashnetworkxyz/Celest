/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.limbo.Savable;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;
import xyz.dashnetwork.celest.utils.storage.Storage;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.Optional;
import java.util.UUID;

public class OfflineUser implements Savable {

    protected final String username;
    protected final UUID uuid;
    protected final String stringUuid;
    protected UserData userData;

    protected OfflineUser(String username, UUID uuid) {
        this.username = username;
        this.uuid = uuid;
        this.stringUuid = uuid.toString();
        this.userData = Storage.read(stringUuid, Storage.Directory.USER, UserData.class);
    }

    public static OfflineUser getOfflineUser(PlayerProfile profile) {
        UUID uuid = profile.getUuid();
        Optional<Player> optional = Celest.getServer().getPlayer(uuid);

        if (optional.isPresent())
            return User.getUser(optional.get());

        return new OfflineUser(profile.getUsername(), uuid);
    }

    @Override
    public void save() { Storage.write(stringUuid, Storage.Directory.USER, userData); }

    public String getUsername() { return username; }

    public UUID getUUID() { return uuid; }

    public void setData(UserData userData) { this.userData = userData; }

    public UserData getData() { return userData; }

}
