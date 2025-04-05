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

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.PunishUtils;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.chat.builder.Section;
import xyz.dashnetwork.celest.chat.builder.formats.OfflineUserFormat;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.profile.OfflineUser;
import xyz.dashnetwork.celest.profile.ProfileUtils;
import xyz.dashnetwork.celest.storage.data.PunishData;
import xyz.dashnetwork.celest.storage.data.UserData;

import java.util.Optional;
import java.util.UUID;

public final class CommandSeen extends CelestCommand {

    public CommandSeen() {
        super("seen");

        addArguments(true, ArgumentType.OFFLINE_USER);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<User> user = User.getUser(source);

        OfflineUser offline = arguments.required(OfflineUser.class);
        UserData data = offline.getData();
        MessageBuilder builder = new MessageBuilder();

        String uuid = offline.getUuid().toString();
        String address = data.getAddress();
        PunishData ban = data.getBan();
        PunishData mute = data.getMute();

        builder.append("&6&l»&7 ");
        builder.append(new OfflineUserFormat(offline)).color(NamedTextColor.GOLD);

        if (address == null)
            builder.append("&7 has never joined the server before.");
        else if (offline.isActive() && user.map(u -> u.canSee((User) offline)).orElse(true))
            builder.append("&7 is &aonline&7.");
        else
            builder.append("&7 is &coffline&7.");

        builder.append("\n&6&l»&7 UUID: ");
        builder.append("&6" + uuid)
                .hover("&7Click to copy &6" + uuid)
                .click(ClickEvent.suggestCommand(uuid));

        if (user.map(User::showAddress).orElse(true) && address != null) {
            builder.append("\n&6&l»&7 Address: ");
            builder.append("&6" + address)
                    .hover("&7Click to copy &6" + address)
                    .click(ClickEvent.suggestCommand(address));
        }

        if (PunishUtils.isValid(ban)) {
            builder.append("\n&6&l»&7 Ban: ");

            Long duration = ban.expiration();
            Section section;
            String date = duration == null ? null : TimeUtils.longToDate(duration);

            if (duration == null)
                section = builder.append("&6Permanent");
            else
                section = builder.append("&6Until " + date);

            UUID judge = ban.judge();
            String name = judge == null ? "Console" : ProfileUtils.fromUuid(judge).getName();

            section.hover("&7Banned by &6" + name);

            if (duration != null)
                section.hover("&7Expires on &6" + date);

            section.hover("&7For: &6" + ban.reason());
        }

        if (PunishUtils.isValid(mute)) {
            builder.append("\n&6&l»&7 Mute: ");

            Long duration = mute.expiration();
            Section section;
            String date = duration == null ? null : TimeUtils.longToDate(duration);

            if (duration == null)
                section = builder.append("&6Permanent");
            else
                section = builder.append("&6Until " + date);

            UUID judge = mute.judge();
            String name = judge == null ? "Console" : ProfileUtils.fromUuid(judge).getName();

            section.hover("&7Muted by &6" + name);

            if (duration != null)
                section.hover("&7Expires on &6" + date);

            section.hover("&7For: &6" + mute.reason());
        }

        builder.message(source);
    }

}
