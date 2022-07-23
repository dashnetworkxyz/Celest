/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.vault;

import com.velocitypowered.api.proxy.Player;

public interface Vault {

    String getPrefix(Player player);

    String getSuffix(Player player);

}
