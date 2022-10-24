/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.data;

import xyz.dashnetwork.celest.utils.profile.PlayerProfile;

public final class AddressData {

    private PlayerProfile[] profiles;
    private PunishData ban, mute;

    public AddressData(PlayerProfile... profiles) {
        this.profiles = profiles;
        ban = null;
        mute = null;
    }

    // User data

    public PlayerProfile[] getProfiles() { return profiles; }

    public void setProfiles(PlayerProfile... profiles) { this.profiles = profiles; }

    // Celest data

    public PunishData getBan() { return ban; }

    public PunishData getMute() { return mute; }

    public void setBan(PunishData ban) { this.ban = ban; }

    public void setMute(PunishData mute) { this.mute = mute; }

}
