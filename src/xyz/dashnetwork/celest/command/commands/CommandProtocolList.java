package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.network.ProtocolVersion;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.ListUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.PageBuilder;

public final class CommandProtocolList extends CelestCommand {

    public CommandProtocolList() { super("protocollist", "protocols"); }

    @Override
    public void execute(CommandSource source, String label, Arguments arguments) {
        PageBuilder page = new PageBuilder();

        for (ProtocolVersion version : ProtocolVersion.values())
            if (ProtocolVersion.isSupported(version))
                page.append("&6" + version.getProtocol() + "&7: "
                        + ListUtils.convertToString(version.getVersionsSupportedBy(), each -> each, ", "));

        MessageUtils.message(source, page::build);
    }

}
