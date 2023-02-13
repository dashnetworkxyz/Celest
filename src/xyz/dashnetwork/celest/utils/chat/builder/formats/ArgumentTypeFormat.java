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

import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;

public final class ArgumentTypeFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public ArgumentTypeFormat(boolean required, ArgumentType type) {
        String name = type.name().toLowerCase().replace("_", "-");
        String open = required ? "<" : "(";
        String close = required ? ">" : ")";

        TextSection section = new TextSection(open + name + close, null, null);

        if (!required)
            section.hover("&6Optional ");

        switch (type) {
            case CHAT_TYPE:
                section.hover("&6Chat selector&7" +
                        "\nThis requires the name of a Chat type." +
                        "\nChat types: &6Global");
                section.hover(", Local", User::isOwner);
                section.hover(", Staff", User::isStaff);
                section.hover(", Admin", User::isAdmin);
                section.hover(", Owner", User::isOwner);
                break;
            case INTEGER:
                section.hover("&6Integer selector&7" +
                        "\nThis requires an integer." +
                        "\nMust be within -2147483648 to 2147483647");
                break;
            case PLAYER:
                section.hover("&6Player selector&7" +
                        "\nThis requires a player." +
                        "\nCan be specified by &6username&7 or &6UUID&7." +
                        "\n&6@s&7 can be used to specify yourself.");
                break;
            case PLAYER_LIST:
                section.hover("&6Player List selector&7" +
                        "\nThis requires a player." +
                        "\nMultiple players can given by separating with comas." +
                        "\nCan be specified by &6username&7 or &6UUID&7." +
                        "\n&6@s&7 can be used to specify yourself." +
                        "\n&6@a&7 can be used to specify all players.");
                break;
            default:
                section.hover("//TODO");
        }

        sections.add(section);
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
