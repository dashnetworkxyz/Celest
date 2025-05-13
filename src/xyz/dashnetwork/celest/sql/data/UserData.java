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

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import xyz.dashnetwork.celest.chat.ChatChannel;

import java.util.UUID;

@Getter @Setter
public final class UserData implements CacheLoader<UUID, UserData>, RemovalListener<UUID, UserData> {

    private String username, address, nickname;
    private PunishData ban, mute;
    private ChatChannel channel;
    private boolean altSpy, commandSpy, pingSpy, signSpy, serverSpy, vanish, streamerMode;

    public UserData(String username) {
        this.username = username;

        ban = null;
        mute = null;
        channel = ChatChannel.GLOBAL;
        address = null;
        nickname = null;
        altSpy = false;
        commandSpy = false;
        pingSpy = false;
        signSpy = false;
        serverSpy = false;
        vanish = false;
        streamerMode = false;
    }

    @Override
    public UserData load(@NotNull UUID uuid) throws Exception {
        return Storage.read(uuid.toString(), Storage.Directory.USER, UserData.class).orElse(new UserData());
    }

    @Override
    public void onRemoval(@Nullable UUID key, @Nullable UserData value, @NotNull RemovalCause cause) {

    }

}
