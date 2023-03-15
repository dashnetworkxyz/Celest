/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.chat.Messages;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;

import java.util.Optional;

public final class CommandTalk extends CelestCommand {

    public CommandTalk() {
        super("talk");

        setPermission(User::isOwner, true);
        addArguments(ArgumentType.CHAT_TYPE, ArgumentType.MESSAGE);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<ChatType> optionalChatType = arguments.get(ChatType.class);
        Optional<String> optionalMessage = arguments.get(String.class);

        if (optionalChatType.isEmpty() || optionalMessage.isEmpty()) {
            sendUsage(source, label);
            return;
        }

        Messages.chatMessage(NamedSource.console, optionalChatType.get(), optionalMessage.get());
    }

}
