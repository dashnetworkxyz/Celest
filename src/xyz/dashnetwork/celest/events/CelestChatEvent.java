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

import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.connection.User;

public final class CelestChatEvent {

    private final User user;
    private final ChatType chatType;
    private final String message;

    public CelestChatEvent(User user, ChatType chatType, String message) {
        this.user = user;
        this.chatType = chatType;
        this.message = message;
    }

    public User getUser() { return user; }

    public ChatType getChatType() { return chatType; }

    public String getMessage() { return message; }

}
