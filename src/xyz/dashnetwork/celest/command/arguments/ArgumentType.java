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

package xyz.dashnetwork.celest.command.arguments;

import xyz.dashnetwork.celest.command.arguments.parsers.*;
import xyz.dashnetwork.celest.command.arguments.suggesters.*;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.Collections;
import java.util.List;

public enum ArgumentType {

    ADDRESS("ip address", new AddressParser(), new AddressSuggester()),
    CHANNEL("channel", new ChannelParser(), new ChannelSuggester()),
    INTEGER("integer", new IntegerParser(), new IntegerSuggester()),
    LONG("duration", new LongParser(), new LongSuggester()),
    OFFLINE_USER("target", new OfflineUserParser(), new OfflineUserSuggester()),
    OFFLINE_USER_LIST("target(s)", new OfflineUserListParser(), new OfflineUserListSuggester()),
    PLAYER("target", new PlayerParser(), new PlayerSuggester()),
    PLAYER_LIST("target(s)", new PlayerListParser(), new PlayerListSuggester()),
    SERVER("server", new ServerParser(), new ServerSuggester()),
    UNIQUE_ID("uuid", new UniqueIdParser(), (user, string) -> Collections.emptyList()),
    STRING("string", (user, string) -> string, (user, string) -> Collections.emptyList()),
    MESSAGE("message", (user, string) -> string, (user, string) -> Collections.emptyList());

    private final String name;
    private final Parser parser;
    private final Suggester suggester;

    ArgumentType(String name, Parser parser, Suggester suggester) {
        this.name = name;
        this.parser = parser;
        this.suggester = suggester;
    }

    public String getName() { return name; }

    public Object parse(User user, String text) { return parser.parse(user, text); }

    public List<String> suggest(User user, String input) { return suggester.suggest(user, input); }

}
