package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.TimeUtils;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.PageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.Section;
import xyz.dashnetwork.celest.utils.connection.User;
import xyz.dashnetwork.celest.utils.limbo.Limbo;
import xyz.dashnetwork.celest.utils.profile.OfflineUser;
import xyz.dashnetwork.celest.utils.storage.Storage;
import xyz.dashnetwork.celest.utils.storage.data.PunishData;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.util.List;
import java.util.Map;

public final class CommandMuteList extends CelestCommand {

    public CommandMuteList() {
        super("mutelist");

        setPermission(User::isAdmin, true);
    }

    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        MessageUtils.message(source, "&6&l»&7 Reading userdata...");

        List<Limbo<OfflineUser>> limbos = Limbo.getAll(OfflineUser.class, each -> each.getData().getBan() != null);
        Map<String, UserData> map = Storage.readAll(Storage.Directory.USER, UserData.class);
        PageBuilder builder = new PageBuilder();

        for (Limbo<OfflineUser> limbo : limbos) {
            OfflineUser offline = limbo.getObject();

            map.put(offline.getUuid().toString(), offline.getData());
        }

        for (Map.Entry<String, UserData> entry : map.entrySet()) {
            UserData data = entry.getValue();
            PunishData mute = data.getMute();

            if (mute == null)
                continue;

            Section section = builder.append("&6" + data.getUsername())
                    .insertion(entry.getKey());

            Long expiration = mute.expiration();

            if (expiration == null)
                section.hover("&7Expiration: &6Permanent");
            else
                section.hover("&7Expiration: &6" + TimeUtils.longToDate(expiration));

            section.hover("&7Reason: &6" + mute.reason());
        }

        MessageUtils.message(source, builder::build);
    }

}