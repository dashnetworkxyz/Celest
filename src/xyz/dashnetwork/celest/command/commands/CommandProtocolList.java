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
import com.velocitypowered.api.network.ProtocolVersion;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.PageBuilder;

public final class CommandProtocolList extends CelestCommand {

    public CommandProtocolList() { super("protocollist", "protocols"); }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        PageBuilder page = new PageBuilder();

        for (ProtocolVersion version : ProtocolVersion.values())
            if (ProtocolVersion.isSupported(version))
                page.append("&6" + version.getProtocol() + "&7: "
                        + ListUtils.convertToString(version.getVersionsSupportedBy(), each -> each, ", "));

        MessageUtils.message(source, page::build);
    }

}
