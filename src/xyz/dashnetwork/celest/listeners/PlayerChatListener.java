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
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.events.CelestChatEvent;
import xyz.dashnetwork.celest.utils.PunishUtils;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.chat.builder.formats.ArgumentTypeFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.ProfileUtils;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.function.Predicate;

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
            String type = expiration == -1 ? "permanently" : "temporarily";

            MessageBuilder builder = new MessageBuilder();
            TextSection section = builder.append("&6&l»&7 You have been " + type + " muted. Hover for more information.");

            section.hover("&7You were muted by &6" + ProfileUtils.fromUuid(mute.getJudge()).getUsername());

            if (expiration != -1)
                section.hover("\n&7Your mute will expire on &6" + TimeUtils.longToDate(expiration));

            section.hover("\n\n" + mute.getReason());

            MessageUtils.message(player, builder::build);
            return;
        }

        String message = event.getMessage();
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

        Celest.getServer().getEventManager().fireAndForget(new CelestChatEvent(user, type, message));

        MessageBuilder builder = new MessageBuilder();
        Predicate<User> predicate;

        switch (type) {
            case OWNER:
                builder.append("&9&lOwner&r ");
                builder.append(new NamedSourceFormat(user));
                builder.append("&r &c&l»&c");

                predicate = each -> each.isOwner() || each.getData().getChatType().equals(ChatType.OWNER);
                break;
            case ADMIN:
                builder.append("&9&lAdmin&r ");
                builder.append(new NamedSourceFormat(user));
                builder.append("&r &3&l»&3");

                predicate = each -> each.isAdmin() || each.getData().getChatType().equals(ChatType.ADMIN);
                break;
            case STAFF:
                builder.append("&9&lStaff&r ");
                builder.append(new NamedSourceFormat(user));
                builder.append("&r &6&l»&6");

                predicate = each -> each.isStaff() || each.getData().getChatType().equals(ChatType.STAFF);
                break;
            case LOCAL:
                player.spoofChatInput(message);
                return;
            default:
                builder.append(new NamedSourceFormat(user));
                builder.append("&r &e&l»&r");

                predicate = each -> true;
        }

        for (String split : message.split(" ")) {
            if (split.length() > 0) {
                TextSection section = builder.append(" " + split);

                if (StringUtils.matchesUrl(split)) {
                    String url = split.toLowerCase().startsWith("http") ? split : "https://" + split;

                    section.hover("&7Click to open &6" + url).click(ClickEvent.openUrl(url));
                }
            }
        }

        MessageUtils.broadcast(predicate, builder::build);
    }

}
