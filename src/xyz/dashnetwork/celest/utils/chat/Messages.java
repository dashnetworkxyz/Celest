/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat;

import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;

public final class Messages {

    public static Component commandCelestHelp() {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 /celest legacy-data-import &c(can overwrite modern data)");
        builder.append("\n&6&l»&7 /celest userdata <list|(uuid)>");
        builder.append("\n&6&l»&7 /celest addressdata <list|(ip)>");

        return builder.build();
    }

    public static Component commandUsage(String alias, String arguments) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 /" + alias + " " + arguments);

        return builder.build();
    }

    public static Component joinServer(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&a&l»&r ");
        builder.append(displayname).hover("&6" + username);
        builder.append("&a joined the server.");

        return builder.build();
    }

    public static Component joinServerVanished(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&3&l»&r ");
        builder.append(displayname).hover("&6" + username);
        builder.append("&3 silently joined the server.");

        return builder.build();
    }

    public static Component leaveServer(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&c&l»&r ");
        builder.append(displayname).hover("&6" + username);
        builder.append("&c left the server.");

        return builder.build();
    }

    public static Component leaveServerVanished(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&3&l»&r ");
        builder.append(displayname).hover("&6" + username);
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
        builder.append(displayname).hover("&6" + username);
        builder.append("&r &e&l»&r ");
        builder.append(message);

        return builder.build();
    }

    public static Component playerChatAdmin(String username, String displayname, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&9&lAdmin&r ");
        builder.append(displayname).hover("&6" + username);
        builder.append("&r &6&l»&r ");
        builder.append("&3" + message);

        return builder.build();
    }

    public static Component playerChatOwner(String username, String displayname, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&9&lOwner&r ");
        builder.append(displayname).hover("&6" + username);
        builder.append("&r &6&l»&r ");
        builder.append("&c" + message);

        return builder.build();
    }

    public static Component playerChatStaff(String username, String displayname, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&9&lStaff&r ");
        builder.append(displayname).hover("&6" + username);
        builder.append("&r &6&l»&r ");
        builder.append("&6" + message);

        return builder.build();
    }

    public static Component playerCommandSpy(String username, String displayname, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&c&lCS&r ");
        builder.append(displayname).hover("&6" + username);
        builder.append(" &e&l»&r ");
        builder.append("&b" + message);

        return builder.build();
    }

    public static Component playerMuted(String reason, String username) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 You have been permanently muted. &7Hover for more information.")
                .hover("&7You were muted by &6" + username
                        + "\n\n&6" + reason);

        return builder.build();
    }

    public static Component playerMutedTemporary(String reason, String username, String expiration) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 You have been temporarily muted. &7Hover for more information.")
                .hover("&7You were muted by &6" + username
                        + "\n&7Your mute will expire on &6" + expiration
                        + "\n\n&6" + reason);

        return builder.build();
    }

    public static Component playerTablistFooter() {
        MessageBuilder builder = new MessageBuilder();
        builder.append("\n&6play.dashnetwork.xyz");

        return builder.build();
    }

    public static Component playerTablistHeader(String server) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&lDashNetwork");
        builder.append("\n&7You are connected to &6" + server + "\n");

        return builder.build();
    }

    public static Component userdataInfo(String uuid, String address, String username, String nickname,
                                         String ban, String mute, String chatType, String lastPlayed,
                                         String altSpy, String commandSpy, String pingSpy, String vanish) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 Hover to view data for &6" + uuid)
                .hover("&7address: &6" + address
                        + "\n&7username: &6" + username
                        + "\n&7nickname: &6" + nickname
                        + "\n&7ban: &6" + ban
                        + "\n&7mute: &6" + mute
                        + "\n&7chatType: &6" + chatType
                        + "\n&7lastPlayed: &6" + lastPlayed
                        + "\n&7altSpy: &6" + altSpy
                        + "\n&7commandSpy: &6" + commandSpy
                        + "\n&7pingSpy: &6" + pingSpy
                        + "\n&7vanish: &6" + vanish);

        return builder.build();
    }

    public static Component welcome(String username, String displayname) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&6 Welcome, ");
        builder.append(displayname).hover("&6" + username);
        builder.append("&6, to &lDashNetwork");

        return builder.build();
    }

}
