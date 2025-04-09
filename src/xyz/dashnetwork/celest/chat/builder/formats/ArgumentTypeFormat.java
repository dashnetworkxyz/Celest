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

package xyz.dashnetwork.celest.chat.builder.formats;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.utils.GrammarUtil;
import xyz.dashnetwork.celest.chat.builder.Format;
import xyz.dashnetwork.celest.chat.builder.sections.ComponentSection;
import xyz.dashnetwork.celest.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class ArgumentTypeFormat implements Format {

    private final List<ComponentSection> sections = new ArrayList<>();

    public ArgumentTypeFormat(ArgumentType type, boolean required) {
        String name = type.getName();
        String open = required ? "<" : "[";
        String close = required ? ">" : "]";

        ComponentSection section = new ComponentSection(open + name + close);

        if (required)
            section.hover("&6Required:");
        else
            section.hover("&6Optional:");

        switch (type) {
            case ADDRESS -> section.hover("""
                    &7Specify one public &6IPv4 address&7 to use.
                    
                    Example:
                    &b54.54.255.255""");
            case CHANNEL -> {
                section.hover("""
                        &7Specify one &6chat channel&7.
                        Channels: &bGlobal""");
                section.hover("&7, &bLocal", User::isOwner);
                section.hover("&7, &bStaff", User::isStaff);
                section.hover("&7, &bAdmin", User::isAdmin);
                section.hover("&7, &bOwner", User::isOwner);
            }
            case INTEGER -> section.hover("""
                    &7Specify an &6integer&7.
                    Some commands may only accept positive numbers.
                    
                    Examples:
                    &b-2147483648
                    2147483647""");
            case LONG -> section.hover("""
                    &7Duration in milliseconds.
                    Specify a &6long&7 or &6time&7.
                    Some commands may only accept positive numbers.
                    
                    Examples:
                    &b1h
                    2weeks
                    -9223372036854775808
                    9223372036854775807""");
            case OFFLINE_USER -> section.hover("""
                    &7Specify &6one online or offline player&7.
                    The player can be specified by &6username&7 or &6UUID&7.
                    &6@s&7 specifies yourself.
                    
                    Examples:
                    &b069a79f4-44e9-4726-a5be-fca90e38aaf5
                    Notch""");
            case OFFLINE_USER_LIST -> section.hover("""
                    &7Specify &6one or multiple online or offline player(s)&7.
                    Multiple players must be separated with commas.
                    Players can be specified by &6username&7 or &6UUID&7.
                    &6@s&7 specifies yourself.
                    &6@a&7 specifies all online players.
                    
                    Examples:
                    &b069a79f4-44e9-4726-a5be-fca90e38aaf5,@s
                    @a,Notch""");
            case PLAYER -> section.hover("""
                    &7Specify &6one online player&7.
                    The player can be specified by &6username&7 or &6UUID&7.
                    &6@s&7 specifies yourself.
                    
                    Examples:
                    &b069a79f4-44e9-4726-a5be-fca90e38aaf5
                    Notch""");
            case PLAYER_LIST -> section.hover("""
                    &7Specify &6one or multiple online player(s)&7.
                    Multiple players must be separated with commas.
                    Players can be specified by &6username&7 or &6UUID&7.
                    &6@s&7 specifies yourself.
                    &6@a&7 specifies all online players.
                    
                    Examples:
                    &b069a79f4-44e9-4726-a5be-fca90e38aaf5,@s
                    Notch,Jeb_""");
            case SERVER -> {
                section.hover("""
                        &7Specify one &6server&7.
                        Servers:\040""");

                boolean comma = false;

                for (RegisteredServer server : Celest.getServer().getAllServers()) {
                    String serverName = GrammarUtil.capitalization(server.getServerInfo().getName());
                    String node = "dashnetwork.server." + serverName.toLowerCase();
                    Predicate<User> predicate = each -> each.isOwner() || each.getPlayer().hasPermission(node);

                    if (comma)
                        section.hover("&7, ", predicate);
                    else
                        comma = true;

                    section.hover("&b" + serverName, predicate);
                }
            }
            case UNIQUE_ID -> section.hover("""
                    &7Specify one &6UUID&7.
                    
                    Example:
                    &b069a79f4-44e9-4726-a5be-fca90e38aaf5""");
            case STRING -> section.hover("""
                    &7Specify one &6word&7.
                    No spaces allowed.""");
            case MULTI_STRING -> section.hover("""
                    &7Write your &6message&7.""");
        }

        sections.add(section);
    }

    @Override
    public List<ComponentSection> sections() { return sections; }

}
