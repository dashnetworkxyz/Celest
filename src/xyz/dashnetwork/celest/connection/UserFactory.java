package xyz.dashnetwork.celest.connection;

import com.github.benmanes.caffeine.cache.*;
import org.jspecify.annotations.Nullable;
import xyz.dashnetwork.celest.storage.Storage;
import xyz.dashnetwork.celest.storage.data.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class UserFactory {

    private final Map<UUID, User> userMap;
    private final LoadingCache<UUID, UserData> userDataCache;

    public UserFactory() {
        userMap = new HashMap<>();
        userDataCache = Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .evictionListener(
                        (RemovalListener<UUID, UserData>) (uuid, userData, cause) ->
                                Storage.write(uuid.toString(), Storage.Directory.USER, userData)
                )
                .build(new CacheLoader<UUID, UserData>() {
                    @Override
                    public UserData load(UUID key) throws Exception {
                        return null;
                    }
                });
    }


}
