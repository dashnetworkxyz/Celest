/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.chat;

import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.utils.GrammarUtils;
import xyz.dashnetwork.celest.utils.LazyUtils;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.function.Predicate;

public enum ChatType {

    OWNER(User::isOwner, "@oc", "@dc"),
    ADMIN(User::isAdmin, "@ac"),
    STAFF(User::isStaff, "@sc"),
    LOCAL(User::isOwner, "@lc"),
    GLOBAL(user -> user.isStaff() ||
            LazyUtils.anyEquals(user.getData().getChatType(), OWNER, ADMIN, STAFF, LOCAL), "@gc");

    private final Predicate<User> permission;
    private final String[] selectors;

    ChatType(Predicate<User> permission, String... selectors) {
        this.permission = permission;
        this.selectors = selectors;
    }

    public String getName() { return GrammarUtils.capitalization(name().toLowerCase()); }

    public boolean hasPermission(User user) {
        return user == null || permission.test(user) || user.getData().getChatType().equals(this);
    }

    public String[] getSelectors() { return selectors; }

    public static ChatType parseSelector(@NotNull String message) {
        if (message.length() < 3)
            return null;

        for (ChatType type : values())
            if (LazyUtils.anyEqualsIgnoreCase(message.substring(0, 3), type.selectors))
                return type;

        return null;
    }

}
