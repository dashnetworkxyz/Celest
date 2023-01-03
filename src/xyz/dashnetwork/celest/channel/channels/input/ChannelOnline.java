/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

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
        boolean showVanished = input.readBoolean();
        int online = 0;

        for (User user : User.getUsers())
            if (showVanished || !user.getData().getVanish())
                online++;

        output.writeInt(online);
    }

}
