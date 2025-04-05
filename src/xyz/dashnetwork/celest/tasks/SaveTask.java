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

package xyz.dashnetwork.celest.tasks;

import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.limbo.Limbo;
import xyz.dashnetwork.celest.storage.Cache;

public final class SaveTask implements Runnable {

    @Override
    public void run() {
        for (User user : User.getUsers())
            user.save();

        for (Limbo<?> limbo : Limbo.getLimbos())
            limbo.save();

        Cache.save();
    }

}
