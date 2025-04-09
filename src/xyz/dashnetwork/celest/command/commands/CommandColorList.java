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
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.chat.MessageUtil;
import xyz.dashnetwork.celest.connection.User;

import java.util.Optional;

public final class CommandColorList extends CelestCommand {

    private static final Component message = LegacyComponentSerializer.legacySection().deserialize("""
            §6§l»§7 Color codes:
            §6§l»§1 &1 §2&2 §3&3 §4&4 §5&5 §6&6 §7&7 §8&8 §9&9 §0&0 §a&a §b&b §c&c §d&d §e&e §f&f
            §6§l»§f §k&k§f(&k) §l&l§f §m&m§f §n&n§f §o&o""");

    public CommandColorList() {
        super("colorlist", "colors");

        addArguments(User::isOwner, true, ArgumentType.PLAYER_LIST);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<Player[]> optional = arguments.optional(Player[].class);

        if (optional.isEmpty())
            MessageUtil.message(source, message);
        else
            for (Player player : optional.get())
                MessageUtil.message(player, message);
    }

}
