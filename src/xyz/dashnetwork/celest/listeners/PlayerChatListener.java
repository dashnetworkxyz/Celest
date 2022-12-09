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
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.Messages;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;
import xyz.dashnetwork.celest.utils.storage.data.UserData;
import xyz.dashnetwork.celest.utils.profile.ProfileUtils;

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
            String banner = ProfileUtils.fromUuid(mute.getBanner()).getUsername();
            String date = TimeUtils.longToDate(expiration);

            Component message = expiration == -1 ?
                    Messages.playerMuted(reason, banner) :
                    Messages.playerMutedTemporary(reason, banner, date);

            MessageUtils.message(player, message);
            return;
        }

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
        final Format format = new PlayerFormat(user);

        switch (type) {
            case OWNER:
                MessageUtils.broadcast(each -> each.isOwner() || each.getData().getChatType().equals(ChatType.OWNER), each ->
                        Messages.playerChatOwner(each, format, finalMessage));
                break;
            case ADMIN:
                MessageUtils.broadcast(each -> each.isAdmin() || each.getData().getChatType().equals(ChatType.ADMIN), each ->
                        Messages.playerChatAdmin(each, format, finalMessage));
                break;
            case STAFF:
                MessageUtils.broadcast(each -> each.isStaff() || each.getData().getChatType().equals(ChatType.STAFF), each ->
                        Messages.playerChatStaff(each, format, finalMessage));
                break;
            case LOCAL:
                player.spoofChatInput(message); // Removes chat signatures
                break;
            default:
                MessageUtils.broadcast(each -> Messages.playerChat(each, format, finalMessage));
        }

        Celest.getServer().getEventManager().fireAndForget(new CelestChatEvent(user, type, finalMessage));
    }

}
