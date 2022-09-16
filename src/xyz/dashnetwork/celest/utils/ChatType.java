/*
 * Copyright (C) 2022 Andrew Bell. - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils;

import xyz.dashnetwork.celest.User;

import java.util.function.Predicate;

public enum ChatType {

    OWNER(UserData::getOwnerChat, User::isOwner, "@oc", "@dc"),
    ADMIN(UserData::getAdminChat, User::isAdmin, "@ac"),
    STAFF(UserData::getStaffChat, User::isStaff, "@sc"),
    LOCAL(UserData::getLocalChat, User::isOwner, "@lc"),
    GLOBAL(userdata -> false, user -> true, "@gc");

    private final Predicate<UserData> userdata;
    private final Predicate<User> permission;
    private final String[] selectors;

    ChatType(Predicate<UserData> userdata, Predicate<User> permission, String... selectors) {
        this.userdata = userdata;
        this.permission = permission;
        this.selectors = selectors;
    }

    public boolean hasPermission(User user) { return permission.test(user) || userdata.test(user.getData()); }

    public static ChatType fromUserdata(UserData data) {
        for (ChatType type : values())
            if (type.userdata.test(data))
                return type;
        return ChatType.GLOBAL;
    }

    public static ChatType parseTag(String message) {
        if (message.length() < 4)
            return null;

        for (ChatType type : values())
            for (String selector : type.selectors)
                if (message.substring(0, 3).equalsIgnoreCase(selector))
                    return type;

        return null;
    }

}
