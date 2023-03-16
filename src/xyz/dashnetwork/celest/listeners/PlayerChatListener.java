/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.utils.PunishUtils;
import xyz.dashnetwork.celest.utils.SecretUtils;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.Messages;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.chat.builder.formats.ArgumentTypeFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.ProfileUtils;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.UUID;

public final class PlayerChatListener {

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerChat(PlayerChatEvent event) {
        event.setResult(PlayerChatEvent.ChatResult.denied());

        Player player = event.getPlayer();
        User user = User.getUser(player);
        UserData userData = user.getData();
        String message = event.getMessage();

        if (!user.isAuthenticated()) {
            if (message.equals(SecretUtils.getTOTP(userData.getTwoFactor()))) {
                user.getData().setAuthenticated(true);

                Channel.callOut("twofactor", user);

                MessageUtils.message(player, "&6&l»&7 You have been successfully authenticated.");
                return;
            }

            MessageUtils.message(player, "&6&l»&7 Invalid TOTP code.");
            return;
        }

        PunishData mute = user.getMute();

        if (!PunishUtils.isValid(mute))
            mute = user.getAddress().getData().getMute();

        if (PunishUtils.isValid(mute)) {
            Long expiration = mute.getExpiration();
            UUID uuid = mute.getJudge();

            String type = expiration == null ? "permanently" : "temporarily";
            String judge = uuid == null ? "Console" : ProfileUtils.fromUuid(uuid).username();

            MessageBuilder builder = new MessageBuilder();
            TextSection section = builder.append("&6&l»&7 You have been " + type + " muted. Hover for more info.");

            section.hover("&7You were muted by &6" + judge);

            if (expiration != null)
                section.hover("\n&7Your mute will expire on &6" + TimeUtils.longToDate(expiration));

            section.hover("\n\n" + mute.getReason());

            MessageUtils.message(player, builder::build);
            return;
        }

        ChatType type = ChatType.parseSelector(message);

        if (type == null)
            type = userData.getChatType();
        else if (type.hasPermission(user)) {
            message = message.substring(3);

            if (message.isBlank()) {
                MessageBuilder builder = new MessageBuilder();
                builder.append("&6&l»&c Usage:&7 " + type.getSelectors()[0]);
                builder.append(new ArgumentTypeFormat(true, ArgumentType.MESSAGE)).prefix("&7");

                MessageUtils.message(player, builder::build);
                return;
            }
        } else
            type = ChatType.GLOBAL;

        Messages.chatMessage(user, type, message);
    }

}
