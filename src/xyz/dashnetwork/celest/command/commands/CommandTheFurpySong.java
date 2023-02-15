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
        String message = "&7The Furpy Song: by Skullrama's Crazy Friend" +
                "\n" +
                "\n&7(Theme from Ikuroshitsuji closing season 1)" +
                "\n" +
                "\n&6One day at my lunch, Skull told me 'bout a bunch." +
                "\nThen days after that, my life changed forever." +
                "\n" +
                "\nBut now I won't waste any time, cause something is on my mind." +
                "\nSomeone that is awesome, a 54 kid." +
                "\n" +
                "\nFur- PEE, Fur- PEE, oh yeah, Furpy my one and only, reach for the ceiling." +
                "\nFur- PEE A Fur and a Pee." +
                "\nMy one, and only Furpy." +
                "\n" +
                "\nIt doesn't matter who you are, Even if you are married and have kids, Root Beer and Panda Wilson." +
                "\nX + 1 does that matter?" +
                "\n" +
                "\nAll my friends they ran away, but you just come back and stay." +
                "\nThis love song to you." +
                "\nYeah, you 54 kid." +
                "\n" +
                "\nFur- PEE, fur- PEE, oh yeah." +
                "\nPlease don't be a girl I'd be depressed." +
                "\nReach for the ceiling My Furpy." +
                "\n" +
                "\nMy one and only Furpy, a Fur and a Pee, you are..." +
                "\nmy only..." +
                "\nyou are" +
                "\nmy true" +
                "\nFURPY!!" +
                "\n" +
                "\n&7(Applause)";

        MessageUtils.message(source, message);
    }

}