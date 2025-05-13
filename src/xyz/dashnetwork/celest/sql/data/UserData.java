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

package xyz.dashnetwork.celest.sql.data;

import lombok.Getter;
import lombok.Setter;
import xyz.dashnetwork.celest.chat.ChatChannel;

import java.util.UUID;

@Getter @Setter
public final class UserData {

    private UUID uuid;
    private String username, address, nickname;
    private ChatChannel channel;
    private boolean altSpy, commandSpy, pingSpy, signSpy, serverSpy, vanish, streamerMode;

    public UserData(UUID uuid, String username, String address, String nickname, ChatChannel channel,
                    boolean altSpy, boolean commandSpy, boolean pingSpy, boolean signSpy, boolean serverSpy,
                    boolean streamerMode) {
        this.uuid = uuid;
        this.username = username;
        this.address = address;
        this.nickname = nickname;
        this.channel = channel;
        this.altSpy = altSpy;
        this.commandSpy = commandSpy;
        this.pingSpy = pingSpy;
        this.signSpy = signSpy;
        this.serverSpy = serverSpy;
        this.streamerMode = streamerMode;
    }

}
