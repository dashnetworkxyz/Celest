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
        builder.append("&6" + displayname).hover("&6" + username);
        builder.append("&a joined the server.");

        return builder.build();
    }

    public static Component joinServerVanished(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&3&l» ");
        builder.append("&6" + displayname).hover("&6" + username);
        builder.append("&3 silently joined the server.");

        return builder.build();
    }

    public static Component leaveServer(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&c&l» ");
        builder.append("&6" + displayname).hover("&6" + username);
        builder.append("&c left the server.");

        return builder.build();
    }

    public static Component leaveServerVanished(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&3&l» ");
        builder.append("&6" + displayname).hover("&6" + username);
        builder.append("&3 silently left the server.");

        return builder.build();
    }

    public static Component loginBanned(String reason, String username) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&lDashNetwork\n");
        builder.append("&7You have been permanently banned\n");
        builder.append("&7You were banned by &6" + username + "\n\n");
        builder.append("&6" + reason);

        return builder.build();
    }

    public static Component loginBannedTemporary(String reason, String username, String expiration) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&lDashNetwork\n");
        builder.append("&7You have been temporarily banned\n");
        builder.append("&7You were banned by &6" + username + "\n");
        builder.append("&7Your ban will expire on &6" + expiration + "\n\n");
        builder.append("&6" + reason);

        return builder.build();
    }

    public static Component playerChat(String username, String displayname, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&7" + displayname).hover("&6" + username);
        builder.append(" &e&l>&f ");
        builder.append(message);

        return builder.build();
    }

    public static Component welcome(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l» &6Welcome, ");
        builder.append(displayname).hover("&6" + username);
        builder.append("&6, to &lDashNetwork");

        return builder.build();
    }

}
