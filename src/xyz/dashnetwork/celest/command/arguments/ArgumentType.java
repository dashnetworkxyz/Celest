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

    ADDRESS(new AddressParser(), new AddressSuggester()),
    CHAT_TYPE(new ChatTypeParser(), new ChatTypeSuggester()),
    INTEGER(new IntegerParser(), new IntegerSuggester()),
    LONG(new LongParser(), new LongSuggester()),
    OFFLINE_USER(new OfflineUserParser(), new OfflineUserSuggester()),
    PLAYER(new PlayerParser(), new PlayerSuggester()),
    PLAYER_LIST(new PlayerListParser(), new PlayerListSuggester()),
    SERVER(new ServerParser(), new ServerSuggester()),
    UNIQUE_ID(new UniqueIdParser(), (user, string) -> Collections.emptyList()),
    STRING((user, string) -> string, (user, string) -> Collections.emptyList()),
    MESSAGE((user, string) -> string, (user, string) -> Collections.emptyList());

    private final Parser parser;
    private final Suggester suggester;

    ArgumentType(Parser parser, Suggester suggester) {
        this.parser = parser;
        this.suggester = suggester;
    }

    public Object parse(User user, String text) { return parser.parse(user, text); }

    public List<String> suggest(User user, String input) { return suggester.suggest(user, input); }

}
