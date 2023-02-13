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

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import xyz.dashnetwork.celest.channel.Channel;

public final class PluginMessageListener {

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (event.getSource() instanceof ServerConnection) {
            ChannelMessageSink sink = (ChannelMessageSink) event.getSource();
            boolean handled = Channel.callIn(event.getIdentifier(), sink, event.dataAsDataStream());

            if (handled)
                event.setResult(PluginMessageEvent.ForwardResult.handled());
        }
    }

}
