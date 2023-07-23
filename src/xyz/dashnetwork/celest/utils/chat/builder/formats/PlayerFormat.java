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

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import xyz.dashnetwork.celest.utils.VersionUtils;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.sections.ComponentSection;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class PlayerFormat implements Format {

    private final List<ComponentSection> sections = new ArrayList<>();

    public PlayerFormat(Player player) {
        User user = User.getUser(player);
        String uuid = user.getUuid().toString();
        String username = player.getUsername();
        String displayname = user.getDisplayname();
        String address = user.getAddress().getString();
        String version = VersionUtils.getVersionString(player.getProtocolVersion());
        Optional<ServerConnection> optional = player.getCurrentServer();
        Predicate<User> predicate = each -> each.isAdmin() && !each.getData().getHideAddress();

        ComponentSection section = new ComponentSection(displayname);
        section.hover("&6" + username);
        section.hover("&7Address: &6" + address, predicate);
        section.hover("&7Version: &6" + version);
        section.insertion(uuid);

        optional.ifPresent(server -> section.hover("&7Server: &6" + server.getServerInfo().getName()));

        sections.add(section);
    }

    public PlayerFormat(Collection<Player> collection, String separator) {
        for (Player player : collection) {
            if (!sections.isEmpty())
                sections.add(new ComponentSection(separator));

            sections.addAll(new PlayerFormat(player).sections());
        }
    }

    public PlayerFormat(Player[] array, String separator) {
        sections.addAll(new PlayerFormat(List.of(array), separator).sections());
    }

    @Override
    public List<ComponentSection> sections() { return sections; }

}
