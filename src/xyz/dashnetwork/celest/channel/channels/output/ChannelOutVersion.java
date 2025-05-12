package xyz.dashnetwork.celest.channel.channels.output;

import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.util.VersionUtil;
import xyz.dashnetwork.celest.connection.User;

public final class ChannelOutVersion extends Channel {

    @Override
    protected void handle(User user) {
        output.writeUTF(user.getUuid().toString());
        output.writeUTF(VersionUtil.getVersionString(user.getPlayer().getProtocolVersion()));
    }

}
