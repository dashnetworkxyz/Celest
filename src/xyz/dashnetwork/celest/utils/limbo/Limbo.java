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

package xyz.dashnetwork.celest.utils.limbo;

import com.velocitypowered.api.scheduler.ScheduledTask;
import com.velocitypowered.api.scheduler.Scheduler;
import xyz.dashnetwork.celest.Celest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public final class Limbo<T> implements Runnable {

    private static final List<Limbo<?>> limbos = new CopyOnWriteArrayList<>();
    private static final Celest celest = Celest.getInstance();
    private static final Scheduler scheduler = Celest.getServer().getScheduler();
    private final T object;
    private ScheduledTask scheduledTask;

    public Limbo(T object) {
        this.object = object;

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

    @SuppressWarnings("unchecked")
    public static <T> List<Limbo<T>> getAll(Class<T> clazz, Predicate<T> predicate) {
        List<Limbo<T>> list = new ArrayList<>();

        for (Limbo<?> limbo : limbos)
            if (clazz.isInstance(limbo.object) && predicate.test((T) limbo.object))
                list.add((Limbo<T>) limbo);

        return list;
    }

    private void schedule() {
        scheduledTask = scheduler.buildTask(celest, this).delay(10, TimeUnit.MINUTES).schedule();
    }

    public T getObject() { return object; }

    public void save() {
        if (isSavable())
            ((Savable) object).save();
    }

    public void reset() {
        scheduledTask.cancel();
        
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

    private boolean isSavable() { return Arrays.asList(object.getClass().getInterfaces()).contains(Savable.class); }

}
