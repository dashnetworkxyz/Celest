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
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.GrammarUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public final class CommandMommy extends CelestCommand {

    private final String[] mommies = { "Dash", "Kevin", "Golden", "Matt", "Furp", "Skull", "Hannah", "Crit" };

    public CommandMommy() { super("mommy", "daddy", "grandma", "grandpa", "aunt", "uncle"); }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        String member = GrammarUtils.capitalization(label);

        MessageUtils.message(source, "&6&l»&7 Scanning face for " + member + "...");

        Celest.getServer().getScheduler().buildTask(Celest.getInstance(), () -> {
            String random = mommies[ThreadLocalRandom.current().nextInt(mommies.length)];

            MessageUtils.message(source, "&6&l»&7 Face identified. Your " + member + " is &6" + random);
        }).delay(1, TimeUnit.SECONDS).schedule();
    }

}
