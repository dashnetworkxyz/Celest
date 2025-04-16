/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.format.NamedTextColor;
import xyz.dashnetwork.celest.chat.builder.Section;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.utils.PunishUtil;
import xyz.dashnetwork.celest.utils.SecretUtil;
import xyz.dashnetwork.celest.utils.TimeUtil;
import xyz.dashnetwork.celest.chat.ChatChannel;
import xyz.dashnetwork.celest.chat.MessageUtil;
import xyz.dashnetwork.celest.chat.Messages;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.formats.ArgumentTypeFormat;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.utils.ProfileUtil;
import xyz.dashnetwork.celest.storage.data.PunishData;
import xyz.dashnetwork.celest.storage.data.UserData;

import java.util.UUID;

public final class PlayerChatListener {

    @SuppressWarnings("deprecation") // setResult is deprecated, but this is the only option.
    @Subscribe(priority = Short.MAX_VALUE)
    public void onPlayerChat(PlayerChatEvent event) {
        event.setResult(PlayerChatEvent.ChatResult.denied());

        Player player = event.getPlayer();
        User user = User.getUser(player);
        UserData userData = user.getData();
        String message = event.getMessage();

        if (!user.isAuthenticated()) {
            if (message.equals(SecretUtil.getTOTP(userData.getTwoFactor()))) {
                user.getData().setAuthenticated(true);

                xyz.dashnetwork.celest.channel.Channel.callOut("userdata", user);

                MessageUtil.message(player, "&6&l»&7 You have been successfully authenticated.");
                return;
            }

            MessageUtil.message(player, "&6&l»&7 Invalid TOTP code.");
            return;
        }

        PunishData mute = user.getMute();

        if (!PunishUtil.isValid(mute))
            mute = user.getAddress().getData().getMute();

        if (PunishUtil.isValid(mute)) {
            Long expiration = mute.expiration();
            UUID uuid = mute.judge();

            String type = expiration == null ? "permanently" : "temporarily";
            String judge = uuid == null ? "Console" : ProfileUtil.fromUuid(uuid).getName();

            MessageBuilder builder = new MessageBuilder();
            Section section = builder.append("&6&l»&7 You have been " + type + " muted. Hover for more info.");

            section.hover("&7You were muted by &6" + judge);

            if (expiration != null)
                section.hover("&7Your mute will expire on &6" + TimeUtil.longToDate(expiration));

            section.hover(mute.reason());

            builder.message(player);
            return;
        }

        ChatChannel channel = ChatChannel.parseSelector(message);

        if (channel == null) {
            channel = userData.getChannel();

            if (channel == ChatChannel.LOCAL) {
                event.setResult(PlayerChatEvent.ChatResult.allowed());
                return;
            }
        } else if (channel.hasPermission(user)) {
            message = message.substring(3);

            if (message.isBlank()) {
                MessageBuilder builder = new MessageBuilder();
                builder.append("&6&l»&c Usage:&7 " + channel.getSelectors()[0] + "&7");
                builder.append(new ArgumentTypeFormat(ArgumentType.MULTI_STRING, true)).color(NamedTextColor.GRAY);
                builder.message(player);
                return;
            }
        } else
            channel = ChatChannel.GLOBAL;

        Messages.chatMessage(user, channel, message);
    }

}
