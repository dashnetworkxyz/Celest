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
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.OfflineUserFormat;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerProfileFormat;
import xyz.dashnetwork.celest.utils.connection.Address;
import xyz.dashnetwork.celest.utils.connection.OfflineUser;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.PlayerProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CommandAccounts extends CelestCommand {

    public CommandAccounts() {
        super("accounts", "alts");

        setPermission(User::isStaff, true);
        addArguments(ArgumentType.OFFLINE_USER);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<OfflineUser> optional = arguments.get(OfflineUser.class);

        if (optional.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        OfflineUser offline = optional.get();
        Address address = Address.getAddress(offline.getData().getAddress(), true);
        List<PlayerProfile> profiles = new ArrayList<>(List.of(address.getData().getProfiles()));

        profiles.removeIf(profile -> profile.uuid().equals(offline.getUuid()));

        MessageBuilder builder = new MessageBuilder();

        if (profiles.isEmpty()) {
            builder.append("&6&l»&7 No known alts for ");
            builder.append(new OfflineUserFormat(offline)).prefix("&6");
        } else {
            builder.append("&6&l»&7 Known alts for ");
            builder.append(new OfflineUserFormat(offline)).prefix("&6");
            builder.append("&7: ");
            builder.append(new PlayerProfileFormat(profiles)).prefix("&6");
        }

        MessageUtils.message(source, builder::build);
    }

}
