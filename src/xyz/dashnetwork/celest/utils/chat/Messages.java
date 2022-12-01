/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat;

import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.utils.User;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;

public final class Messages {

    public static Component commandUsage(String alias, String... arguments) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&c Usage:&7 /" + alias);

        for (String argument : arguments)
            builder.append(" &7" + argument + "");

        return builder.build(null);
    }

    public static Component joinServer(User user, Format player) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&a&l»&r ");
        builder.append(player);
        builder.append("&a joined.");

        return builder.build(user);
    }

    public static Component joinServerVanished(User user, Format player) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&3&l»&r ");
        builder.append(player);
        builder.append("&3 silently joined.");

        return builder.build(user);
    }

    public static Component leaveServer(User user, Format player) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&c&l»&r ");
        builder.append(player);
        builder.append("&c left.");

        return builder.build(user);
    }

    public static Component leaveServerVanished(User user, Format player) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&3&l»&r ");
        builder.append(player);
        builder.append("&3 silently left.");

        return builder.build(user);
    }

    public static Component loginBanned(String reason, String username) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&lDashNetwork");
        builder.append("\n&7You have been permanently banned");
        builder.append("\n&7You were banned by &6" + username);
        builder.append("\n\n&6" + reason);

        return builder.build(null);
    }

    public static Component loginBannedTemporary(String reason, String username, String expiration) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&lDashNetwork");
        builder.append("\n&7You have been temporarily banned");
        builder.append("\n&7You were banned by &6" + username);
        builder.append("\n&7Your ban will expire on &6" + expiration);
        builder.append("\n\n&6" + reason);

        return builder.build(null);
    }

    public static Component playerChat(User user, Format player, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append(player);
        builder.append("&r &e&l»&r ");
        builder.append(message);

        return builder.build(user);
    }

    public static Component playerChatAdmin(User user, Format player, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&9&lAdmin&r ");
        builder.append(player);
        builder.append("&r &3&l»&r ");
        builder.append("&3" + message);

        return builder.build(user);
    }

    public static Component playerChatOwner(User user, Format player, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&9&lOwner&r ");
        builder.append(player);
        builder.append("&r &c&l»&r ");
        builder.append("&c" + message);

        return builder.build(user);
    }

    public static Component playerChatStaff(User user, Format player, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&9&lStaff&r ");
        builder.append(player);
        builder.append("&r &6&l»&r ");
        builder.append("&6" + message);

        return builder.build(user);
    }

    public static Component playerCommandSpy(User user, Format player, String message) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&9&lCmd&r ");
        builder.append(player);
        builder.append("&r &b&l»&r ");
        builder.append("&b" + message);

        return builder.build(user);
    }

    public static Component playerMuted(String reason, String username) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 You have been permanently muted. Hover for more information.")
                .hover("&7You were muted by &6" + username
                        + "\n\n&6" + reason);

        return builder.build(null);
    }

    public static Component playerMutedTemporary(String reason, String username, String expiration) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 You have been temporarily muted. Hover for more information.")
                .hover("&7You were muted by &6" + username
                        + "\n&7Your mute will expire on &6" + expiration
                        + "\n\n&6" + reason);

        return builder.build(null);
    }

    public static Component playerPingSpy(String name, String clientAddress,
                                          String serverAddress, String serverPort,
                                          String version, String protocol,
                                          String profiles) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&6 " + name + "&7 pinged the server.")
                .hover("&6" + clientAddress
                        + "\n&7Server Address: &6" + serverAddress
                        + "\n&7Server Port: &6" + serverPort
                        + "\n&7Version: &6" + version + "&7 (" + protocol + ")"
                        + "\n&7Profiles: &6" + profiles);

        return builder.build(null);
    }

    public static Component playerTablistFooter() {
        MessageBuilder builder = new MessageBuilder();
        builder.append("\n&6play.dashnetwork.xyz");

        return builder.build(null);
    }

    public static Component playerTablistHeader(String server) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&lDashNetwork");
        builder.append("\n&7You are connected to &6" + server + "\n");

        return builder.build(null);
    }

    // TODO
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

        return builder.build(null);
    }

    public static Component welcome(User user, Format player) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&6 Welcome, ");
        builder.append(player);
        builder.append("&6, to &lDashNetwork");

        return builder.build(user);
    }

}
