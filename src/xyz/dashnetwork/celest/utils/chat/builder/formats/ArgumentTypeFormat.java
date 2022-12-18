/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
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
                        "\nChat types: &6global");
                section.hover("&6, local", User::isOwner);
                section.hover("&6, staff", User::isStaff);
                section.hover("&6, admin", User::isAdmin);
                section.hover("&6, owner", User::isOwner);
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
