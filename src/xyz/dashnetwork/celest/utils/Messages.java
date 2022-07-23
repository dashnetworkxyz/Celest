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
        MessageBuilder message = new MessageBuilder();
        message.append("&a&l» ");
        message.append("&6" + displayname).hover("&6" + username);
        message.append("&a joined the server.");

        return message.build();
    }

    public static Component joinServerVanished(String username, String displayname) {
        MessageBuilder message = new MessageBuilder();
        message.append("&3&l» ");
        message.append("&6" + displayname).hover("&6" + username);
        message.append("&3 silently joined the server.");

        return message.build();
    }

    public static Component leaveServer(String username, String displayname) {
        MessageBuilder message = new MessageBuilder();
        message.append("&c&l» ");
        message.append("&6" + displayname).hover("&6" + username);
        message.append("&c left the server.");

        return message.build();
    }

    public static Component leaveServerVanished(String username, String displayname) {
        MessageBuilder message = new MessageBuilder();
        message.append("&3&l» ");
        message.append("&6" + displayname).hover("&6" + username);
        message.append("&3 silently left the server.");

        return message.build();
    }

    public static Component loginBanned(String reason) {
        MessageBuilder message = new MessageBuilder();
        message.append("&6&lDashNetwork\n");
        message.append("&7You have been banned\n\n");
        message.append("&6" + reason);

        return message.build();
    }

    public static Component loginBannedTemporary(String reason, String expiration) {
        MessageBuilder message = new MessageBuilder();
        message.append("&6&lDashNetwork\n");
        message.append("&7You have been banned\n");
        message.append("&7Your ban will expire on &6" + expiration + "\n\n");
        message.append("&6" + reason);

        return message.build();
    }

    public static Component welcome(String username, String displayname) {
        MessageBuilder message = new MessageBuilder();
        message.append("&6&l» &6Welcome, ");
        message.append(displayname).hover("&6" + username);
        message.append("&6, to &lDashNetwork");

        return message.build();
    }

}
