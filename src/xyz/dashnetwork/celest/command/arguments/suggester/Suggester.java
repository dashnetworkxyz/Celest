/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments.suggester;

import xyz.dashnetwork.celest.utils.connection.User;

import java.util.List;

public interface Suggester {

    List<String> suggest(User user, String input);

}
