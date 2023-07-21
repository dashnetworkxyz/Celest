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

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.format.NamedTextColor;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.OfflineUserFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.OfflineUser;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CommandAltSpy extends CelestCommand {

    public CommandAltSpy() {
        super("altspy", "as");

        setPermission(User::isStaff, true);
        addArguments(User::isOwner, true, ArgumentType.OFFLINE_USER_LIST);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        List<OfflineUser> users = arguments.offlineListOrSelf(source);

        if (users.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        Optional<User> optional = User.getUser(source);
        List<OfflineUser> on = new ArrayList<>();
        List<OfflineUser> off = new ArrayList<>();
        MessageBuilder builder;

        for (OfflineUser offline : users) {
            UserData data = offline.getData();
            boolean spy = !data.getAltSpy();

            data.setAltSpy(spy);

            if (offline.isActive()) {
                builder = new MessageBuilder();
                builder.append("&6&l»&7 You are ");
                builder.append(spy ? "now" : "no longer");
                builder.append(" in &6AltSpy&7.");
                builder.message((User) offline);
            }

            if (optional.map(user -> !user.equals(offline)).orElse(false)) {
                if (spy)
                    on.add(offline);
                else
                    off.add(offline);
            }
        }

        int onSize = on.size();
        int offSize = off.size();

        if (onSize > 0) {
            builder = new MessageBuilder();
            builder.append("&6&l»&6 ");
            builder.append(new OfflineUserFormat(on, "&7, ")).color(NamedTextColor.GOLD);

            if (onSize > 1)
                builder.append("&7 are");
            else
                builder.append("&7 is");

            builder.append(" now in &6AltSpy&7.");
            builder.message(source);
        }

        if (offSize > 0) {
            builder = new MessageBuilder();
            builder.append("&6&l»&6 ");
            builder.append(new OfflineUserFormat(off, "&7, ")).color(NamedTextColor.GOLD);

            if (offSize > 1)
                builder.append("&7 are");
            else
                builder.append("&7 is");

            builder.append(" no longer in &6AltSpy&7.");
            builder.message(source);
        }
    }

}
