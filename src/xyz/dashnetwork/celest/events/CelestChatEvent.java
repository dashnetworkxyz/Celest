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

package xyz.dashnetwork.celest.events;

import xyz.dashnetwork.celest.utils.chat.Channel;
import xyz.dashnetwork.celest.utils.profile.NamedSource;

public final class CelestChatEvent {

    private final NamedSource named;
    private final Channel channel;
    private final String message;

    public CelestChatEvent(NamedSource named, Channel channel, String message) {
        this.named = named;
        this.channel = channel;
        this.message = message;
    }

    public NamedSource getNamedSource() { return named; }

    public Channel getChannel() { return channel; }

    public String getMessage() { return message; }

}
