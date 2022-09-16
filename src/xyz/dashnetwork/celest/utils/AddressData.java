/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import java.util.UUID;

public class AddressData {

    // TODO: Create Address class?

    private UUID[] uuids;
    private PunishData ban, mute;

    public AddressData(UUID... uuids) {
        this.uuids = uuids;
        ban = null;
        mute = null;
    }

    // User data

    public UUID[] getUuids() { return uuids; }

    // Celest data

    public PunishData getBan() { return ban; }

    public PunishData getMute() { return mute; }

}
