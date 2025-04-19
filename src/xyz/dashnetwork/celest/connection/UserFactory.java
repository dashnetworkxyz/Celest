package xyz.dashnetwork.celest.connection;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.storage.Storage;
import xyz.dashnetwork.celest.storage.data.UserData;

import java.util.*;
import java.util.concurrent.TimeUnit;

public final class UserFactory {

    private final ProxyServer server = Celest.getServer();
    private final Map<UUID, User> users;
    private final Map<UUID, UserData> userDatas;
    private final Set<UUID> recentlyUsed;

    public UserFactory() {
        this.users = new HashMap<>();
        this.userDatas = new HashMap<>();
        this.recentlyUsed = Collections.newSetFromMap(Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .<UUID, Boolean>removalListener((key, value, result) -> clean(key))
                .build().asMap());

        server.getEventManager().register(Celest.getInstance(), DisconnectEvent.class, event ->
                clean(event.getPlayer().getUniqueId())
        );
    }

    public User getUser(Player player) {
        recentlyUsed.add(player.getUniqueId());

        return users.computeIfAbsent(player.getUniqueId(), uuid -> new User(player));
    }

    public UserData getUserData(UUID uuid) {
        recentlyUsed.add(uuid);

        return userDatas.computeIfAbsent(uuid, u ->
                Storage.read(u.toString(), Storage.Directory.USER, UserData.class)
        );
    }

    public void cleanAll() {
        Set<UUID> set = new HashSet<>(users.keySet());
        set.addAll(userDatas.keySet());

        for (UUID uuid : set)
            clean(uuid);
    }

    private void clean(UUID uuid) {
        if (server.getPlayer(uuid).isEmpty()) {
            users.remove(uuid);
            userDatas.remove(uuid);
        }
    }

}
