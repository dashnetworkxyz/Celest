package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.ArgumentType;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.NamedSourceFormat;
import xyz.dashnetwork.celest.utils.connection.Address;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.profile.NamedSource;
import xyz.dashnetwork.celest.utils.storage.data.AddressData;

public final class CommandUnIpMute extends CelestCommand {

    public CommandUnIpMute() {
        super("unipmute", "unmuteip");

        setPermission(User::isAdmin, true);
        addArguments(true, ArgumentType.ADDRESS);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Address address = arguments.required(Address.class);
        AddressData data = address.getData();

        if (data != null)
            data.setMute(null);

        MessageBuilder builder = new MessageBuilder();
        builder.append("&6&l»&6 " + address.getString() + "&7 unmuted by ");
        builder.append(new NamedSourceFormat(NamedSource.of(source)));
        builder.append("&7.");
        builder.broadcast(User::isStaff);
    }

}
