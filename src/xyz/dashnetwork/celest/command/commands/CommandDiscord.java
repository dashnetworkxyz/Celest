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

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.chat.builder.MessageBuilder;

public final class CommandDiscord extends CelestCommand {

    public CommandDiscord() { super("discord"); }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 Join the discord server at ");
        builder.append("&6https://discord.gg/3RDsNEE")
                .hover("&7Click to open &6https://discord.com/invite/3RDsNEE")
                .click(ClickEvent.openUrl("https://discord.com/invite/3RDsNEE"));
        builder.append("&7.");
        builder.message(source);
    }

}
