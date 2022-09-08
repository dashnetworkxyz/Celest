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

    private PunishData ban, mute;
    private UUID[] uuids;

    public AddressData() {
        this.ban = null;
        this.mute = null;
        this.uuids = null;
    }

    public PunishData getBan() { return ban; }

    public PunishData getMute() { return mute; }

    public UUID[] getUuids() { return uuids; }

}
