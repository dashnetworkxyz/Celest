/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.limbo;

import com.velocitypowered.api.scheduler.ScheduledTask;
import com.velocitypowered.api.scheduler.Scheduler;
import xyz.dashnetwork.celest.Celest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public final class Limbo<T> implements Runnable {

    private static final List<Limbo<?>> limbos = new ArrayList<>();
    private static final Celest celest = Celest.getInstance();
    private static final Scheduler scheduler = Celest.getServer().getScheduler();
    private final T object;
    private boolean shouldSave;
    private ScheduledTask scheduledTask;

    public Limbo(T object) {
        this.object = object;
        this.shouldSave = isSavable();

        schedule();

        limbos.add(this);
    }

    public static List<Limbo<?>> getLimbos() { return limbos; }

    @SuppressWarnings("unchecked")
    public static <T> Limbo<T> get(Class<T> clazz, Predicate<T> predicate) {
        for (Limbo<?> limbo : limbos)
            if (clazz.isInstance(limbo.object) && predicate.test((T) limbo.object))
                return (Limbo<T>) limbo;

        return null;
    }

    private void schedule() {
        scheduledTask = scheduler.buildTask(celest, this).delay(10, TimeUnit.MINUTES).schedule();
    }

    public T getObject() { return object; }

    public void save() {
        if (shouldSave) {
            ((Savable) object).save();
            shouldSave = false;
        }
    }

    public void reset() {
        scheduledTask.cancel();
        shouldSave = isSavable();
        
        schedule();
    }

    public void cancel() {
        scheduledTask.cancel();
        limbos.remove(this);
    }

    @Override
    public void run() {
        save();
        limbos.remove(this);
    }

    private boolean isSavable() { return object.getClass().isAssignableFrom(Savable.class); }

}
