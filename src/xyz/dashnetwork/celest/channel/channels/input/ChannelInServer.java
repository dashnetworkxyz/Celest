package xyz.dashnetwork.celest.channel.channels.input;

import com.google.common.io.ByteArrayDataInput;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.connection.User;

import java.util.Optional;

public final class ChannelInServer extends Channel {

    @Override
    public void handle(ByteArrayDataInput input) {
        Optional<RegisteredServer> server = Celest.getServer().getServer(input.readUTF());

        if (server.isEmpty())
            return;

        int online = 0;
        int vanished = 0;

        for (Player player : server.get().getPlayersConnected()) {
            online++;

            if (User.getUser(player).getData().getVanish())
                vanished++;
        }

        output.writeInt(online);
        output.writeInt(vanished);
    }

}
