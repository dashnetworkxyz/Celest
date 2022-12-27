/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat;

import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;

@Deprecated
public final class Messages {

    public static Component loginBanned(String reason, String username) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&lDashNetwork");
        builder.append("\n&7You have been permanently banned");
        builder.append("\n&7You were banned by &6" + username);
        builder.append("\n\n" + reason);

        return builder.build(null);
    }

    public static Component loginBannedTemporary(String reason, String username, String expiration) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&lDashNetwork");
        builder.append("\n&7You have been temporarily banned");
        builder.append("\n&7You were banned by &6" + username);
        builder.append("\n&7Your ban will expire on &6" + expiration);
        builder.append("\n\n" + reason);

        return builder.build(null);
    }

    public static Component playerMuted(String reason, String username) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 You have been permanently muted. Hover for more information.")
                .hover("&7You were muted by &6" + username
                        + "\n\n" + reason);

        return builder.build(null);
    }

    public static Component playerMutedTemporary(String reason, String username, String expiration) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 You have been temporarily muted. Hover for more information.")
                .hover("&7You were muted by &6" + username
                        + "\n&7Your mute will expire on &6" + expiration
                        + "\n\n" + reason);

        return builder.build(null);
    }

}
