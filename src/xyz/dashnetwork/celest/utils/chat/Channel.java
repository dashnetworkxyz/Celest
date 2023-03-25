/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils.chat;

import org.jetbrains.annotations.NotNull;
import xyz.dashnetwork.celest.utils.GrammarUtils;
import xyz.dashnetwork.celest.utils.LazyUtils;
import xyz.dashnetwork.celest.utils.PermissionType;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.function.Predicate;

public enum Channel {

    OWNER(PermissionType.OWNER.getPermission(), "@oc", "@dc"),
    ADMIN(PermissionType.ADMIN.getPermission(), "@ac"),
    STAFF(PermissionType.STAFF.getPermission(), "@sc"),
    LOCAL(PermissionType.OWNER.getPermission(), "@lc"),
    GLOBAL(user -> user.isStaff() ||
            LazyUtils.anyEquals(user.getData().getChannel(), OWNER, ADMIN, STAFF, LOCAL), "@gc");

    private final Predicate<User> permission;
    private final String[] selectors;

    Channel(Predicate<User> permission, String... selectors) {
        this.permission = permission;
        this.selectors = selectors;
    }

    public String getName() { return GrammarUtils.capitalization(name().toLowerCase()); }

    public boolean hasPermission(User user) {
        return user == null || permission.test(user) || user.getData().getChannel().equals(this);
    }

    public String[] getSelectors() { return selectors; }

    public static Channel parseSelector(@NotNull String message) {
        if (message.length() < 3)
            return null;

        for (Channel type : values())
            if (LazyUtils.anyEqualsIgnoreCase(message.substring(0, 3), type.selectors))
                return type;

        return null;
    }

}
