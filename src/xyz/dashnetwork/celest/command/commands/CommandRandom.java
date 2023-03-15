/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
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
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;

import java.util.Random;

public final class CommandRandom extends CelestCommand {

    private final Random random = new Random();

    public CommandRandom() {
        super("random", "rng");

        addArguments(ArgumentType.INTEGER);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        int bound = arguments.get(Integer.class).orElse(Integer.MAX_VALUE);
        int generated = random.nextInt(bound);

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 Generated &6" + generated)
                .hover("&7Click to copy &6" + generated)
                .click(ClickEvent.suggestCommand(String.valueOf(generated)));

        MessageUtils.message(source, builder::build);
    }

}