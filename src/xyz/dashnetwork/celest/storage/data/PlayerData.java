package xyz.dashnetwork.celest.storage.data;

import com.google.common.collect.ImmutableList;
import com.velocitypowered.api.util.GameProfile;

import java.util.UUID;

public record PlayerData(UUID uuid, String username) {

    public GameProfile toGameProfile() {
        return new GameProfile(uuid, username, ImmutableList.of());
    }

}
