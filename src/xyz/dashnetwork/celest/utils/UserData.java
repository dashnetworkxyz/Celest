/*
 * Copyright (c) 2022. Andrew Bell.
 * All rights reserved.
 */

package xyz.dashnetwork.celest.utils;

public class UserData {

    private String address;
    private long lastPlayed;
    private boolean adminChat, altSpy, commandSpy, ownerChat, pingSpy, staffChat, vanish;

    // TODO: Add Punish to userdata once system is made.

    public String getAddress() { return address; }

    public long getLastPlayed() { return lastPlayed; }

    public boolean getAdminChat() { return adminChat; }

    public boolean getAltSpy() { return altSpy; }

    public boolean getCommandSpy() { return commandSpy; }

    public boolean getOwnerChat() { return ownerChat; }

    public boolean getPingSpy() { return pingSpy; }

    public boolean getStaffChat() { return staffChat; }

    public boolean getVanish() { return vanish; }

    public void setAddress(String address) { this.address = address; }

    public void setLastPlayed(long lastPlayed) { this.lastPlayed = lastPlayed; }

    public void setAdminChat(boolean adminChat) { this.adminChat = adminChat; }

    public void setAltSpy(boolean altSpy) { this.altSpy = altSpy; }

    public void setCommandSpy(boolean commandSpy) { this.commandSpy = commandSpy; }

    public void setOwnerChat(boolean ownerChat) { this.ownerChat = ownerChat; }

    public void setPingSpy(boolean pingSpy) { this.pingSpy = pingSpy; }

    public void setStaffChat(boolean staffChat) { this.staffChat = staffChat; }

    public void setVanish(boolean vanish) { this.vanish = vanish; }

}
