/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
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

public class CommandMommy extends CelestCommand {

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
