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

package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.event.ClickEvent;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;

import java.nio.ByteBuffer;
import java.util.UUID;

public final class CommandUniqueId extends CelestCommand {

    public CommandUniqueId() {
        super("uniqueid", "uuid");

        addArguments(false, ArgumentType.UNIQUE_ID);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        UUID uuid = arguments.optional(UUID.class).orElse(UUID.randomUUID());

        long most = uuid.getMostSignificantBits();
        long least = uuid.getLeastSignificantBits();
        byte[] bytes = ByteBuffer.allocate(16).putLong(most).putLong(least).array();
        int[] ints = new int[4];

        for (int i = 0; i < 4; i++)
            ints[i] = ByteBuffer.wrap(bytes, i * 4, 4).getInt();

        String stringUuid = uuid.toString();
        String stringLongs = "UUIDMost:" + most + ",UUIDLeast:" + least;
        String stringInts = "Id:[I;" + ints[0] + "," + ints[1] + "," + ints[2] + "," + ints[3] + "]";

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&7 128 Bit: &6" + stringUuid)
                .hover("&6" + stringUuid)
                .click(ClickEvent.suggestCommand(stringUuid));
        builder.append("\n&6&l»&7 UUID Most: &6" + most)
                .hover("&6" + stringLongs)
                .click(ClickEvent.suggestCommand(stringLongs));
        builder.append("\n&6&l»&7 UUID Least: &6" + least)
                .hover("&6" + stringLongs)
                .click(ClickEvent.suggestCommand(stringLongs));
        builder.append("\n&6&l»&7 Int Array:\n&6&l»&6 " + ints[0] + "\n&6&l»&6 " + ints[1] + "\n&6&l»&6 " + ints[2] + "\n&6&l»&6 " + ints[3])
                .hover("&6" + stringInts)
                .click(ClickEvent.suggestCommand(stringInts));
        builder.message(source);
    }

}
