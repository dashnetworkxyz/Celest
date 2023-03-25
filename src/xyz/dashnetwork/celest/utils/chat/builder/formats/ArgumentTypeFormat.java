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

import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.utils.chat.builder.Format;
import xyz.dashnetwork.celest.utils.chat.builder.TextSection;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class ArgumentTypeFormat implements Format {

    private final List<TextSection> sections = new ArrayList<>();

    public ArgumentTypeFormat(boolean required, ArgumentType type) {
        String name = type.getName();
        String open = required ? "<" : "[";
        String close = required ? ">" : "]";

        TextSection section = new TextSection(open + name + close, null, null);

        if (required)
            section.hover("&6Required:\n");
        else
            section.hover("&6Optional:\n");

        switch (type) {
            case ADDRESS -> section.hover("""
                    &7Specify one public &6IPv4 address&7 to use.
                    
                    Example:
                    &654.54.255.255""");
            case CHANNEL -> {
                section.hover("""
                        &7Specify one &6chat channel&7.
                        Channels: &6Global""");
                section.hover("&7, &6Local", User::isOwner);
                section.hover("&7, &6Staff", User::isStaff);
                section.hover("&7, &6Admin", User::isAdmin);
                section.hover("&7, &6Owner", User::isOwner);
            }
            case INTEGER -> section.hover("""
                    &7Specify an &6integer&7.
                    Some commands may only accept positive numbers.
                    
                    Examples:
                    &6-2147483648
                    2147483647""");
            case LONG -> section.hover("""
                    &7Duration in milliseconds.
                    Specify a &6long&7 or &6time&7.
                    Some commands may only accept positive numbers.
                    
                    Examples:
                    &61h
                    2weeks
                    -9223372036854775808
                    9223372036854775807""");
            case OFFLINE_USER -> section.hover("""
                    &7Specify &6one online or offline player&7.
                    The player can be specified by &6username&7 or &6UUID&7.
                    &6@s&7 specifies yourself.
                    
                    Examples:
                    &6069a79f4-44e9-4726-a5be-fca90e38aaf5
                    Notch""");
            case OFFLINE_USER_LIST -> section.hover("""
                    &7Specify &6one or multiple online or offline player(s)&7.
                    Multiple players must be separated with commas.
                    Players can be specified by &6username&7 or &6UUID&7.
                    &6@s&7 specifies yourself.
                    &6@a&7 specifies all online players.
                    
                    Examples:
                    &6069a79f4-44e9-4726-a5be-fca90e38aaf5,@s
                    @a,Notch""");
            case PLAYER -> section.hover("""
                    &7Specify &6one online player&7.
                    The player can be specified by &6username&7 or &6UUID&7.
                    &6@s&7 specifies yourself.
                    
                    Examples:
                    &6069a79f4-44e9-4726-a5be-fca90e38aaf5
                    Notch""");
            case PLAYER_LIST -> section.hover("""
                    &7Specify &6one or multiple online player(s)&7.
                    Multiple players must be separated with commas.
                    Players can be specified by &6username&7 or &6UUID&7.
                    &6@s&7 specifies yourself.
                    &6@a&7 specifies all online players.
                    
                    Examples:
                    &6069a79f4-44e9-4726-a5be-fca90e38aaf5,@s
                    Notch,Jeb_""");
            case SERVER -> {
                section.hover("""
                        &7Specify one &6server&7.
                        Servers:\040""");

                boolean comma = false;

                for (RegisteredServer server : Celest.getServer().getAllServers()) {
                    String serverName = server.getServerInfo().getName();
                    String node = "dashnetwork.server." + serverName.toLowerCase();
                    Predicate<User> predicate = each -> each.isOwner() || each.getPlayer().hasPermission(node);

                    if (comma)
                        section.hover("&7, ", predicate);
                    else
                        comma = true;

                    section.hover("&6" + serverName, predicate);
                }
            }
            case UNIQUE_ID -> section.hover("""
                    &7Specify one &6UUID&7.
                    
                    Example:
                    &6069a79f4-44e9-4726-a5be-fca90e38aaf5""");
            case STRING -> section.hover("""
                    &7Specify one &6word&7.
                    No spaces allowed.""");
            case MESSAGE -> section.hover("""
                    &7Write your &6message&7.""");
        }

        sections.add(section);
    }

    @Override
    public List<TextSection> sections() { return sections; }

}
