/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import net.kyori.adventure.text.Component;

public class Messages {

    public static Component joinServer(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&a&l» ");
        builder.append("&f" + displayname).hover("&6" + username);
        builder.append("&a joined the server.");

        return builder.build();
    }

    public static Component joinServerVanished(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&3&l» ");
        builder.append("&f" + displayname).hover("&6" + username);
        builder.append("&3 silently joined the server.");

        return builder.build();
    }

    public static Component leaveServer(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&c&l» ");
        builder.append("&f" + displayname).hover("&6" + username);
        builder.append("&c left the server.");

        return builder.build();
    }

    public static Component leaveServerVanished(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&3&l» ");
        builder.append("&f" + displayname).hover("&6" + username);
        builder.append("&3 silently left the server.");

        return builder.build();
    }

    public static Component loginBanned(String reason, String username) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&lDashNetwork");
        builder.append("\n&7You have been permanently banned");
        builder.append("\n&7You were banned by &6" + username);
        builder.append("\n\n&6" + reason);

        return builder.build();
    }

    public static Component loginBannedTemporary(String reason, String username, String expiration) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&lDashNetwork");
        builder.append("\n&7You have been temporarily banned");
        builder.append("\n&7You were banned by &6" + username);
        builder.append("\n&7Your ban will expire on &6" + expiration);
        builder.append("\n\n&6" + reason);

        return builder.build();
    }

    public static Component playerChat(String username, String displayname, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&f" + displayname).hover("&6" + username);
        builder.append(" &e&l>&f ");
        builder.append(message);

        return builder.build();
    }

    public static Component playerChatAdmin(String username, String displayname, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&9&lAdmin ");
        builder.append("&f" + displayname).hover("&6" + username);
        builder.append(" &6&l>&3 ");
        builder.append(message);

        return builder.build();
    }

    public static Component playerChatOwner(String username, String displayname, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&9&lOwner ");
        builder.append("&f" + displayname).hover("&6" + username);
        builder.append(" &6&l>&c ");
        builder.append(message);

        return builder.build();
    }

    public static Component playerChatStaff(String username, String displayname, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&9&lStaff ");
        builder.append("&f" + displayname).hover("&6" + username);
        builder.append(" &6&l>&6 ");
        builder.append(message);

        return builder.build();
    }

    public static Component playerMuted(String reason, String username) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l» &7You have been permanently muted. &7Hover for more information.")
                .hover("&7You were muted by &6" + username
                        + "\n\n&6" + reason);

        return builder.build();
    }

    public static Component playerMutedTemporary(String reason, String username, String expiration) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l» &7You have been temporarily muted. &7Hover for more information.")
                .hover("&7You were muted by &6" + username
                        + "\n&7Your mute will expire on &6" + expiration
                        + "\n\n&6" + reason);

        return builder.build();
    }

    public static Component welcome(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l» &6Welcome, ");
        builder.append("&f" + displayname).hover("&6" + username);
        builder.append("&6, to &lDashNetwork");

        return builder.build();
    }

}
