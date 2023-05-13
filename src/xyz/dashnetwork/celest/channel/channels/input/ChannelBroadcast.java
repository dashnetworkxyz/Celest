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
import xyz.dashnetwork.celest.utils.PermissionType;
import xyz.dashnetwork.celest.utils.chat.ComponentUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.function.Predicate;

public final class ChannelBroadcast extends Channel {

    @Override
    public void handle(ByteArrayDataInput input) {
        boolean json = input.readBoolean();
        boolean console = input.readBoolean();
        Predicate<User> permission = PermissionType.valueOf(input.readUTF()).getPredicate();
        String string = input.readUTF();

        if (json)
            MessageUtils.broadcast(console, permission, ComponentUtils.fromJson(string));
        else
            MessageUtils.broadcast(console, permission, string);
    }

}
