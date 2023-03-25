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
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;

public final class CommandTheFurpySong extends CelestCommand {

    public CommandTheFurpySong() { super("thefurpysong", "furpysong"); }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        String message = """
                &7The Furpy Song: by Skullrama's Crazy Friend

                &7(Theme from Ikuroshitsuji closing season 1)

                &6One day at my lunch, Skull told me 'bout a bunch.
                Then days after that, my life changed forever.

                But now I won't waste any time, cause something is on my mind.
                Someone that is awesome, a 54 kid.

                Fur- PEE, Fur- PEE, oh yeah, Furpy my one and only, reach for the ceiling.
                Fur- PEE A Fur and a Pee.
                My one, and only Furpy.

                It doesn't matter who you are, Even if you are married and have kids, Root Beer and Panda Wilson.
                X + 1 does that matter?

                All my friends they ran away, but you just come back and stay.
                This love song to you.
                Yeah, you 54 kid.

                Fur- PEE, fur- PEE, oh yeah.
                Please don't be a girl I'd be depressed.
                Reach for the ceiling My Furpy.

                My one and only Furpy, a Fur and a Pee, you are...
                my only...
                you are
                my true
                FURPY!!

                &7(Applause)""";

        MessageUtils.message(source, message);
    }

}
