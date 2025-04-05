package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.chat.MessageUtils;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.profile.OfflineUser;

public final class CommandRealJoin extends CelestCommand {

    public CommandRealJoin() {
        super("realjoin");

        setPermission(User::isOwner, false);
        addArguments(true, ArgumentType.OFFLINE_USER);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        OfflineUser offline = arguments.required(OfflineUser.class);

        if (offline.isActive()) {
            MessageUtils.message(source, "&6&l»&7 That player is currently online");
            return;
        }

        User user = User.getUser(source).orElseThrow();
        user.setRealJoin(offline.toGameProfile());

        MessageUtils.message(source, "&6&l»&7 You are now queued to join as &6" + offline.getUsername());
    }

}
