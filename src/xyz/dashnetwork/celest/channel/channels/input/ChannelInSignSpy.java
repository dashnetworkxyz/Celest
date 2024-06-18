/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.channel.channels.input;

import com.google.common.io.ByteArrayDataInput;
import com.velocitypowered.api.proxy.Player;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.channel.Channel;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.formats.PlayerFormat;

import java.util.Optional;
import java.util.UUID;

public final class ChannelInSignSpy extends Channel {

    @Override
    protected void handle(ByteArrayDataInput input) {
        UUID uuid = UUID.fromString(input.readUTF());
        int x = input.readInt();
        int y = input.readInt();
        int z = input.readInt();
        String line1 = input.readUTF();
        String line2 = input.readUTF();
        String line3 = input.readUTF();
        String line4 = input.readUTF();

        Optional<Player> optional = Celest.getServer().getPlayer(uuid);

        if (optional.isPresent()) {
            Player player = optional.get();

            MessageBuilder builder = new MessageBuilder();
            builder.append("&6&l»&f ");
            builder.append(new PlayerFormat(player));
            builder.append(" ");
            builder.append("&7placed sign in &6" + server + "&7 at &6" + x + " " + y + " " + z)
                    .hover("&6" + line1 + "\n&6" + line2 + "\n&6" + line3 + "\n&6" + line4);
            builder.append("&7.");
            builder.broadcast(each -> each.getData().getSignSpy());
        }
    }

}
