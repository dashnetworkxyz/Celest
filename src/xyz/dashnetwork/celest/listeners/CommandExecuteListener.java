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

package xyz.dashnetwork.celest.listeners;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;

public final class CommandExecuteListener {

    @Subscribe(order = PostOrder.FIRST)
    public void onCommandExecute(CommandExecuteEvent event) {
        CommandSource source = event.getCommandSource();

        if (source instanceof Player player) {
            User user = User.getUser(player);

            if (!user.isAuthenticated()) {
                event.setResult(CommandExecuteEvent.CommandResult.denied());

                MessageUtils.message(source, "&6&l»&7 Please enter your 2fa code into chat.");
                return;
            }
        }

        String command = "/" + event.getCommand();
        NamedSource named = NamedSource.of(source);

        MessageBuilder builder = new MessageBuilder();
        builder.append("&b&l»&r ");
        builder.append(new NamedSourceFormat(named));
        builder.append(" ");
        builder.append("&b" + command).insertion(command);

        MessageUtils.broadcast(user -> user.getData().getCommandSpy(), builder::build);
    }

}
