package xyz.dashnetwork.celest.utils;

import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public final class CacheUtil {

    public static <K, V> Map<K, V> newExpiringMap(long duration, TimeUnit unit) {
        return Caffeine.newBuilder()
                .expireAfterAccess(duration, unit)
                .removalListener()
                .<K, V>build().asMap();
    }

    public static <E> Set<E> newExpiringSet(long duration, TimeUnit unit) {
        return Collections.newSetFromMap(newExpiringMap(duration, unit));
    }

}
