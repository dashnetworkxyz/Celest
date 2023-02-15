/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils.storage.data;

import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.utils.chat.ChatType;

public final class UserData {

    private String username, address, twoFactor, nickname;
    private PunishData ban, mute;
    private ChatType chatType;
    private Long lastPlayed;
    private boolean authenticated, altSpy, commandSpy, pingSpy, signSpy, vanish, hideAddress;

    public UserData(String username) {
        this.username = username;

        ban = null;
        mute = null;
        chatType = ChatType.GLOBAL;
        address = null;
        nickname = null;
        twoFactor = null;
        lastPlayed = null;
        authenticated = false;
        altSpy = false;
        commandSpy = false;
        pingSpy = false;
        signSpy = false;
        vanish = false;
        hideAddress = false;
    }

    // User data

    public String getAddress() { return address; }

    public String getUsername() { return username; }

    public void setAddress(String address) { this.address = address; }

    public void setUsername(String username) { this.username = username; }

    // Celest data

    public String getTwoFactor() { return twoFactor; }

    public String getNickName() { return nickname; }

    public PunishData getBan() { return ban; }

    public PunishData getMute() { return mute; }

    public ChatType getChatType() { return chatType; }

    public Long getLastPlayed() { return lastPlayed; }

    public boolean getAuthenticated() { return authenticated; }

    public boolean getAltSpy() { return altSpy; }

    public boolean getCommandSpy() { return commandSpy; }

    public boolean getPingSpy() { return pingSpy; }

    public boolean getSignSpy() { return signSpy; }

    public boolean getVanish() { return vanish; }

    public boolean getHideAddress() { return hideAddress; }

    public void setTwoFactor(String twoFactor) { this.twoFactor = twoFactor; }

    public void setNickName(String nickname) { this.nickname = nickname; }

    public void setBan(PunishData ban) { this.ban = ban; }

    public void setMute(PunishData mute) { this.mute = mute; }

    public void setChatType(@NotNull ChatType chatType) { this.chatType = chatType; }

    public void setLastPlayed(long lastPlayed) { this.lastPlayed = lastPlayed; }

    public void setAuthenticated(boolean authenticated) { this.authenticated = authenticated; }

    public void setAltSpy(boolean altSpy) { this.altSpy = altSpy; }

    public void setCommandSpy(boolean commandSpy) { this.commandSpy = commandSpy; }

    public void setPingSpy(boolean pingSpy) { this.pingSpy = pingSpy; }

    public void setSignSpy(boolean signSpy) { this.signSpy = signSpy; }

    public void setVanish(boolean vanish) { this.vanish = vanish; }

    public void setHideAddress(boolean hideAddress) { this.hideAddress = hideAddress; }

    public boolean isObsolete() {
        return ban == null
                && mute == null
                && chatType == ChatType.GLOBAL
                && address == null
                && nickname == null
                && lastPlayed == null
                && !altSpy
                && !commandSpy
                && !pingSpy
                && !signSpy
                && !vanish
                && !hideAddress;
    }

}