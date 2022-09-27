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
        AddressData addressData = user.getAddress().getData();
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
                String banner = ProfileUtils.fromUuid(selectedMute.getBanner()).getUsername();
                String date = TimeUtils.longToDate(expiration);

                Component message = expiration == -1 ?
                        Messages.playerMuted(reason, banner) :
                        Messages.playerMutedTemporary(reason, banner, date);

                MessageUtils.message(player, message);
                return;
            }
        }

        String username = player.getUsername();
        String displayname = user.getDisplayname();
        String message = event.getMessage();
        ChatType type = ChatType.parseTag(message);

        if (type == null)
            type = userData.getChatType();
        else if (type.hasPermission(user))
            message = message.substring(3);
        else
            type = ChatType.GLOBAL;

        switch (type) {
            case OWNER:
                MessageUtils.broadcast(each -> each.isOwner() || each.getData().getChatType().equals(ChatType.OWNER),
                        Messages.playerChatOwner(username, displayname, message));
                break;
            case ADMIN:
                MessageUtils.broadcast(each -> each.isAdmin() || each.getData().getChatType().equals(ChatType.ADMIN),
                        Messages.playerChatAdmin(username, displayname, message));
                break;
            case STAFF:
                MessageUtils.broadcast(each -> each.isStaff() || each.getData().getChatType().equals(ChatType.STAFF),
                        Messages.playerChatStaff(username, displayname, message));
                break;
            case LOCAL:
                player.spoofChatInput(message); // Removes chat signatures
                break;
            default:
                MessageUtils.broadcast(Messages.playerChat(username, displayname, message));
        }
    }

}
