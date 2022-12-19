/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.events.CelestChatEvent;
import xyz.dashnetwork.celest.utils.PunishUtils;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.Messages;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.ProfileUtils;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

public final class PlayerChatListener {

    @Subscribe
    public void onPlayerChat(PlayerChatEvent event) {
        event.setResult(PlayerChatEvent.ChatResult.denied());

        Player player = event.getPlayer();
        User user = User.getUser(player);
        UserData userData = user.getData();
        PunishData mute = user.getMute();

        if (!PunishUtils.isValid(mute))
            mute = user.getAddress().getData().getMute();

        if (PunishUtils.isValid(mute)) {
            long expiration = mute.getExpiration();
            String reason = mute.getReason();
            String judge = ProfileUtils.fromUuid(mute.getJudge()).getUsername();
            String date = TimeUtils.longToDate(expiration);

            // TODO: Find a good replacement for Messages here. New Format maybe?
            Component message = expiration == -1 ?
                    Messages.playerMuted(reason, judge) :
                    Messages.playerMutedTemporary(reason, judge, date);

            MessageUtils.message(player, message);
            return;
        }

        // TODO: URL ClickEvent generation.
        String message = event.getMessage();
        ChatType type = ChatType.parseSelector(message);

        if (type == null)
            type = userData.getChatType();
        else if (type.hasPermission(user)) {
            message = message.substring(3);

            if (message.isBlank()) {
                MessageUtils.message(player, "&6&l»&c Usage:&7 " + type.getSelectors()[0] + "<message>");
                return;
            }
        } else
            type = ChatType.GLOBAL;

        final String finalMessage = message;
        final MessageBuilder builder = new MessageBuilder();

        switch (type) {
            case OWNER:
                builder.append("&9&lOwner&r ");
                builder.append(new NamedSourceFormat(user));
                builder.append("&r &c&l»&c " + message);

                MessageUtils.broadcast(
                        each -> each.isOwner() || each.getData().getChatType().equals(ChatType.OWNER),
                        builder::build
                );
                break;
            case ADMIN:
                builder.append("&9&lAdmin&r ");
                builder.append(new NamedSourceFormat(user));
                builder.append("&r &3&l»&3 " + message);

                MessageUtils.broadcast(
                        each -> each.isAdmin() || each.getData().getChatType().equals(ChatType.ADMIN),
                        builder::build
                );
                break;
            case STAFF:
                builder.append("&9&lStaff&r ");
                builder.append(new NamedSourceFormat(user));
                builder.append("&r &6&l»&6 " + message);

                MessageUtils.broadcast(
                        each -> each.isStaff() || each.getData().getChatType().equals(ChatType.STAFF),
                        builder::build
                );
                break;
            case LOCAL:
                player.spoofChatInput(message);
                break;
            default:
                builder.append(new NamedSourceFormat(user));
                builder.append("&r &e&l»&r " + message);

                MessageUtils.broadcast(builder::build);
        }

        Celest.getServer().getEventManager().fireAndForget(new CelestChatEvent(user, type, finalMessage));
    }

}
