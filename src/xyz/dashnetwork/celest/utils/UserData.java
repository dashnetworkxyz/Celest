/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

public class UserData {

    private String address, username, nickname;
    private PunishData ban, mute;
    private ChatType chatType;
    private long lastPlayed;
    private boolean altSpy, commandSpy, pingSpy, vanish;

    public UserData() {
        ban = null;
        mute = null;
        chatType = ChatType.GLOBAL;
        address = null;
        username = null;
        nickname = null;
        lastPlayed = -1;
        altSpy = false;
        commandSpy = false;
        pingSpy = false;
        vanish = false;
    }

    // User data

    public String getAddress() { return address; }

    public String getUsername() { return username; }

    public void setAddress(String address) { this.address = address; }

    public void setUsername(String username) { this.username = username; }

    // Celest data

    public String getNickname() { return nickname; }

    public PunishData getBan() { return ban; }

    public PunishData getMute() { return mute; }

    public ChatType getChatType() { return chatType; }

    public long getLastPlayed() { return lastPlayed; }

    public boolean getAltSpy() { return altSpy; }

    public boolean getCommandSpy() { return commandSpy; }

    public boolean getPingSpy() { return pingSpy; }

    public boolean getVanish() { return vanish; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public void setBan(PunishData ban) { this.ban = ban; }

    public void setMute(PunishData mute) { this.mute = mute; }

    public void setChatType(ChatType chatType) { this.chatType = chatType; }

    public void setLastPlayed(long lastPlayed) { this.lastPlayed = lastPlayed; }

    public void setAltSpy(boolean altSpy) { this.altSpy = altSpy; }

    public void setCommandSpy(boolean commandSpy) { this.commandSpy = commandSpy; }

    public void setPingSpy(boolean pingSpy) { this.pingSpy = pingSpy; }

    public void setVanish(boolean vanish) { this.vanish = vanish; }

}
