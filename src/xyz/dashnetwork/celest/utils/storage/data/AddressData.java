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

package xyz.dashnetwork.celest.utils.storage.data;

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
