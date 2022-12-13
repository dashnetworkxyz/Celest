/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.messages.ChannelMessageSink;
import xyz.dashnetwork.celest.channel.Channel;

public final class PluginMessageListener {

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (event.getSource() instanceof ChannelMessageSink) {
            ChannelMessageSink sink = (ChannelMessageSink) event.getSource();
            boolean handled = Channel.callIn(event.getIdentifier(), sink, event.dataAsDataStream());

            if (handled)
                event.setResult(PluginMessageEvent.ForwardResult.handled());
        }
    }

}
