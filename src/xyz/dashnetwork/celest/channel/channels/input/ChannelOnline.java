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

package xyz.dashnetwork.celest.channel.channels.input;

import com.google.common.io.ByteArrayDataInput;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.utils.connection.User;

public final class ChannelOnline extends Channel {

    @Override
    public void handle(ByteArrayDataInput input) {
        int vanished = 0;
        int online = 0;

        for (User user : User.getUsers()) {
            online++;

            if (user.getData().getVanish())
                vanished++;
        }

        output.writeInt(online);
        output.writeInt(vanished);
    }

}
