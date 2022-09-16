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

    OWNER(UserData::getOwnerChat, User::isOwner),
    ADMIN(UserData::getAdminChat, User::isAdmin),
    STAFF(UserData::getStaffChat, User::isStaff),
    LOCAL(UserData::getLocalChat, User::isOwner),
    GLOBAL(userdata -> true, user -> true);

    private final Predicate<UserData> userdata;
    private final Predicate<User> permission;

    ChatType(Predicate<UserData> userdata, Predicate<User> permission) {
        this.userdata = userdata;
        this.permission = permission;
    }

    public boolean hasPermission(User user) { return permission.test(user) || userdata.test(user.getData()); }

    public static ChatType fromUserdata(UserData data) {
        for (ChatType type : values())
            if (type.userdata.test(data)) // TODO: Test that enum is ordered in values()
                return type;
        return ChatType.GLOBAL;
    }

    public static ChatType parseTag(String message) {
        if (message.length() < 4)
            return null;

        switch (message.substring(0, 3).toLowerCase()) {
            case "@oc":
            case "@dc":
                return ChatType.OWNER;
            case "@ac":
                return ChatType.ADMIN;
            case "@sc":
                return ChatType.STAFF;
            case "@lc":
                return ChatType.LOCAL;
            case "@gc":
                return ChatType.GLOBAL;
            default:
                return null;
        }
    }

}
