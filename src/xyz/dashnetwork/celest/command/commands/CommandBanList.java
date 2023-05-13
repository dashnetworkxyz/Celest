/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
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
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.PageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.storage.Storage;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.Map;

public class CommandBanList extends CelestCommand {

    public CommandBanList() {
        super("banlist");

        setPermission(User::isAdmin, true);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        MessageUtils.message(source, "&6&l»&7 Reading userdata...");

        // TODO: Make sure this accounts for limbo
        Map<String, UserData> map = Storage.readAll(Storage.Directory.USER, UserData.class);
        PageBuilder builder = new PageBuilder();

        for (Map.Entry<String, UserData> entry : map.entrySet()) {
            UserData data = entry.getValue();
            PunishData ban = data.getBan();

            if (ban == null)
                continue;

            TextSection section = builder.append("&6" + data.getUsername())
                    .insertion(entry.getKey());

            Long expiration = ban.expiration();

            if (expiration == null)
                section.hover("&7Expiration: &6Permanent");
            else
                section.hover("&7Expiration: &6" + TimeUtils.longToDate(expiration));

            section.hover("\n&7Reason: &6" + ban.reason());
        }

        MessageUtils.message(source, builder::build);
    }

}
