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
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.PageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.Section;
import xyz.dashnetwork.celest.utils.connection.Address;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.limbo.Limbo;
import xyz.dashnetwork.celest.utils.storage.Storage;
import xyz.dashnetwork.celest.utils.storage.data.AddressData;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;

import java.util.List;
import java.util.Map;

public final class CommandIpMuteList extends CelestCommand {

    public CommandIpMuteList() {
        super("ipmutelist", "muteiplist");

        setPermission(User::isAdmin, true);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        MessageUtils.message(source, "&6&l»&7 Reading addressdata...");

        List<Limbo<Address>> limbos = Limbo.getAll(Address.class, each -> each.getData().getMute() != null);
        Map<String, AddressData> map = Storage.readAll(Storage.Directory.ADDRESS, AddressData.class);
        PageBuilder builder = new PageBuilder();

        for (Limbo<Address> limbo : limbos) {
            Address address = limbo.getObject();

            map.put(address.getString(), address.getData());
        }

        for (Map.Entry<String, AddressData> entry : map.entrySet()) {
            String address = entry.getKey();
            AddressData data = entry.getValue();
            PunishData mute = data.getMute();

            if (mute == null)
                continue;

            Section section = builder.append("&6" + address)
                    .insertion(address);

            Long expiration = mute.expiration();

            if (expiration == null)
                section.hover("&7Expiration: &6Permanent");
            else
                section.hover("&7Expiration: &6" + TimeUtils.longToDate(expiration));

            section.hover("&7Reason: &6" + mute.reason());
        }

        MessageUtils.message(source, builder::build);
    }

}
