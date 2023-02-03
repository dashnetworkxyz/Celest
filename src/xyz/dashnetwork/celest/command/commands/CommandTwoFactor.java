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
import xyz.dashnetwork.celest.utils.SecretUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class CommandTwoFactor extends CelestCommand {

    private final Map<UUID, String> tempKeyMap = new HashMap<>();

    public CommandTwoFactor() {
        super("twofactor", "2fa");

        setPermission(User::isStaff, false);
        addArguments(ArgumentType.STRING);
        addArguments(ArgumentType.STRING);
        setCompletions(0, "setup", "disable", "verify");
    }

    private void sendHelpMessage(CommandSource source, String label) {
        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&6 Two-Factor Commands");
        builder.append("\n&6&l»&7 /" + label + " setup").hover("&6Setup 2fa.");
        builder.append("\n&6&l»&7 /" + label + " disable").hover("&6Disable 2fa.");
        builder.append("\n&6&l»&7 /" + label + " verify").hover("&6Verify 2fa code during setup");

        MessageUtils.message(source, builder::build);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Optional<String> optionalCommand = arguments.get(String.class);
        Optional<String> optionalCode = arguments.get(String.class);

        if (optionalCommand.isEmpty()) {
            sendHelpMessage(source, label);
            return;
        }

        User user = User.getUser(source);
        assert user != null;

        UUID uuid = user.getUuid();
        UserData data = user.getData();
        String twoFactor = data.getTwoFactor();

        switch (optionalCommand.get().toLowerCase()) {
            case "setup":
                if (twoFactor != null) {
                    MessageUtils.message(source, "&6&l»&7 You have already setup 2fa.");
                    return;
                }

                String generated = SecretUtils.generateSecret();
                tempKeyMap.put(uuid, generated);

                MessageBuilder builder = new MessageBuilder();
                builder.append("&6&l»&7 Your generated key is ");
                builder.append("&6" + generated).hover("&7Click to copy &6" + generated).click(ClickEvent.suggestCommand(generated));
                builder.append("\n&6&l»&7 Do not share this key with anyone.");
                builder.append("\n&6&l»&7 Type &6/" + label + " verify <totp>&7 to finish 2fa setup.");

                MessageUtils.message(source, builder::build);
                break;
            case "disable":
                if (twoFactor == null) {
                    MessageUtils.message(source, "&6&l»&7 You haven't setup 2fa.");
                    return;
                }

                if (optionalCode.isEmpty()) {
                    MessageUtils.message(source, "&6&l»&7 You need to provide a TOTP to disable 2fa.");
                    return;
                }

                if (optionalCode.get().equals(SecretUtils.getTOTP(twoFactor))) {
                    data.setTwoFactor(null);
                    data.setAuthenticated(false);

                    MessageUtils.message(source, "&6&l»&7 You have disabled 2fa.");
                    return;
                }

                MessageUtils.message(source, "&6&l»&7 Incorrect TOTP code.");
                break;
            case "verify":
            case "totp":
                if (!tempKeyMap.containsKey(uuid)) {
                    MessageUtils.message(source, "&6&l»&7 Use &6/" + label + " setup&7 to setup 2fa.");
                    return;
                }

                if (optionalCode.isEmpty()) {
                    MessageUtils.message(source, "&6&l»&7 You need to provide a TOTP.");
                    return;
                }

                String secret = tempKeyMap.get(uuid);

                if (optionalCode.get().equals(SecretUtils.getTOTP(secret))) {
                    tempKeyMap.remove(uuid);

                    data.setTwoFactor(secret);
                    data.setAuthenticated(true);

                    MessageUtils.message(source, "&6&l»&7 2fa has been successfully setup.");
                    return;
                }

                MessageUtils.message(source, "&6&l»&7 Incorrect TOTP code.");
                break;
            default:
                sendHelpMessage(source, label);
        }
    }

}
