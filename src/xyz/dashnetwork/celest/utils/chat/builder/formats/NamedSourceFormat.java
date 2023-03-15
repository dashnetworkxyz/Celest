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

package xyz.dashnetwork.celest.utils.chat.builder.formats;

import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class NamedSourceFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public NamedSourceFormat(NamedSource... sources) { this(List.of(sources)); }

    public NamedSourceFormat(Collection<NamedSource> sources) {
        for (NamedSource named : sources) {
            if (!sections.isEmpty())
                sections.add(new TextSection("&6, ", null, null));

            sections.addAll(new NamedSourceFormat(named).sections());
        }
    }

    public NamedSourceFormat(NamedSource source) {
        String username = source.getUsername();
        String displayname = source.getDisplayname();
        TextSection section = new TextSection(displayname, "&6" + username, null);

        if (source instanceof User) {
            User user = (User) source;

            section.hover("\n&7Address: &6" + user.getAddress().getString(), User::showAddress);
            section.insertion(user.getUuid().toString());

            Player player = user.getPlayer();

            player.getCurrentServer().ifPresent(server -> {
                String name = server.getServerInfo().getName();

                section.hover("\n&7Server: &6" + name,
                        each -> each.getPlayer().hasPermission("dashnetwork.server." + name.toLowerCase()));
            });
        }

        sections.add(section);
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
