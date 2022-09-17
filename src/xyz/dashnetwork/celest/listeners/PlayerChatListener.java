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
import xyz.dashnetwork.celest.User;
import xyz.dashnetwork.celest.utils.*;

public class PlayerChatListener {

    @Subscribe
    public void onPlayerChat(PlayerChatEvent event) {
        event.setResult(PlayerChatEvent.ChatResult.denied());

        Player player = event.getPlayer();
        User user = User.getUser(player);
        AddressData addressData = user.getAddress().getAddressData();
        UserData userData = user.getData();
        PunishData userMute = userData.getMute();
        PunishData ipMute = addressData.getMute();

        if (userMute != null || ipMute != null) {
            PunishData selectedMute = userMute;

            if (selectedMute == null)
                selectedMute = ipMute;

            long expiration = selectedMute.getExpiration();

            if (expiration == -1 || expiration > System.currentTimeMillis()) {
                String reason = selectedMute.getReason();
                Component message = expiration == -1 ?
                        Messages.playerMuted(reason, "") : // TODO: Username pulling
                        Messages.playerMutedTemporary(reason, "", ""); // TODO: TimeUtils

                MessageUtils.message(player, message);
                return;
            }
        }

        String username = player.getUsername();
        String displayname = user.getDisplayname();
        String message = event.getMessage();
        ChatType type = ChatType.parseTag(message);

        if (type == null)
            type = ChatType.fromUserdata(userData);
        else if (type.hasPermission(user))
            message = message.substring(3);
        else
            type = ChatType.GLOBAL;

        // TODO: Find a way to replace User::isOwnerOrOwnerchat, User::isAdminOrAdminchat, & User::isStaffOrStaffchat

        switch (type) {
            case OWNER:
                MessageUtils.broadcast(User::isOwnerOrOwnerchat, Messages.playerChatOwner(username, displayname, message));
                break;
            case ADMIN:
                MessageUtils.broadcast(User::isAdminOrAdminchat, Messages.playerChatAdmin(username, displayname, message));
                break;
            case STAFF:
                MessageUtils.broadcast(User::isStaffOrStaffchat, Messages.playerChatStaff(username, displayname, message));
                break;
            case LOCAL:
                player.spoofChatInput(message); // Removes chat signatures
                break;
            default:
                MessageUtils.broadcast(Messages.playerChat(username, displayname, message));
        }
    }

}
