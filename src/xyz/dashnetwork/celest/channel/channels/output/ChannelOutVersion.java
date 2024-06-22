package xyz.dashnetwork.celest.channel.channels.output;

import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.utils.VersionUtils;
import xyz.dashnetwork.celest.utils.connection.User;

public final class ChannelOutVersion extends Channel {

    @Override
    protected void handle(User user) {
        output.writeUTF(user.getUuid().toString());
        output.writeUTF(VersionUtils.getVersionString(user.getPlayer().getProtocolVersion()));
    }

}
