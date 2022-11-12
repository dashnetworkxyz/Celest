/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.events;

import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.chat.ChatType;

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
