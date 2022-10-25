/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.events;

import com.velocitypowered.api.event.ResultedEvent;

public final class CelestPacketEvent implements ResultedEvent<ResultedEvent.GenericResult> {

    private GenericResult result = GenericResult.allowed();

    public CelestPacketEvent() {
        // TODO
    }

    @Override
    public GenericResult getResult() { return result; }

    @Override
    public void setResult(GenericResult result) { this.result = result; }

}
