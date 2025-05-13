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
import xyz.dashnetwork.celest.chat.builder.Section;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.util.TimeUtil;
import xyz.dashnetwork.celest.chat.MessageUtil;
import xyz.dashnetwork.celest.chat.builder.PageBuilder;
import xyz.dashnetwork.celest.connection.Address;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.sql.data.AddressData;
import xyz.dashnetwork.celest.sql.data.PunishData;

import java.util.List;
import java.util.Map;

public final class CommandIpBanList extends CelestCommand {

    public CommandIpBanList() {
        super("ipbanlist", "baniplist");

        setPermission(User::isAdmin, true);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        MessageUtil.message(source, "&6&l»&7 Reading addressdata...");

        List<Limbo<Address>> limbos = Limbo.getAll(Address.class, each -> each.getData().getBan() != null);
        Map<String, AddressData> map = Storage.readAll(Storage.Directory.ADDRESS, AddressData.class);
        PageBuilder builder = new PageBuilder();

        for (Limbo<Address> limbo : limbos) {
            Address address = limbo.getObject();

            map.put(address.getString(), address.getData());
        }

        for (Map.Entry<String, AddressData> entry : map.entrySet()) {
            String address = entry.getKey();
            AddressData data = entry.getValue();
            PunishData ban = data.getBan();

            if (ban == null)
                continue;

            Section section = builder.append("&6" + address)
                    .insertion(address);

            Long expiration = ban.expiration();

            if (expiration == null)
                section.hover("&7Expiration: &6Permanent");
            else
                section.hover("&7Expiration: &6" + TimeUtil.longToDate(expiration));

            section.hover("&7Reason: &6" + ban.reason());
        }

        MessageUtil.message(source, builder::build);
    }

}
