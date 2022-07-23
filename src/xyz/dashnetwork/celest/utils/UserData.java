/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

public class UserData {

    private PunishData ban, mute;
    private String address, nickname;
    private long lastPlayed;
    private boolean adminChat, altSpy, commandSpy, ownerChat, pingSpy, staffChat, vanish;

    public UserData() {
        ban = null;
        mute = null;
        address = null;
        nickname = null;
        lastPlayed = -1;
        adminChat = false;
        altSpy = false;
        commandSpy = false;
        ownerChat = false;
        pingSpy = false;
        staffChat = false;
        vanish = false;
    }

    public PunishData getBan() { return ban; }

    public PunishData getMute() { return mute; }

    public String getAddress() { return address; }

    public String getNickname() { return nickname; }

    public long getLastPlayed() { return lastPlayed; }

    public boolean getAdminChat() { return adminChat; }

    public boolean getAltSpy() { return altSpy; }

    public boolean getCommandSpy() { return commandSpy; }

    public boolean getOwnerChat() { return ownerChat; }

    public boolean getPingSpy() { return pingSpy; }

    public boolean getStaffChat() { return staffChat; }

    public boolean getVanish() { return vanish; }

    public void setBan(PunishData ban) { this.ban = ban; }

    public void setMute(PunishData mute) { this.mute = mute; }

    public void setAddress(String address) { this.address = address; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public void setLastPlayed(long lastPlayed) { this.lastPlayed = lastPlayed; }

    public void setAdminChat(boolean adminChat) { this.adminChat = adminChat; }

    public void setAltSpy(boolean altSpy) { this.altSpy = altSpy; }

    public void setCommandSpy(boolean commandSpy) { this.commandSpy = commandSpy; }

    public void setOwnerChat(boolean ownerChat) { this.ownerChat = ownerChat; }

    public void setPingSpy(boolean pingSpy) { this.pingSpy = pingSpy; }

    public void setStaffChat(boolean staffChat) { this.staffChat = staffChat; }

    public void setVanish(boolean vanish) { this.vanish = vanish; }

}
