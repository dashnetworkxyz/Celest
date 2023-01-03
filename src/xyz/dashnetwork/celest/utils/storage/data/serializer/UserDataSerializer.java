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

package xyz.dashnetwork.celest.utils.storage.data.serializer;

import com.google.gson.*;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.lang.reflect.Type;

public final class UserDataSerializer implements JsonSerializer<UserData>, JsonDeserializer<UserData> {

    private static final Gson gson = Celest.getGson();

    @Override
    public JsonElement serialize(UserData userData, Type type, JsonSerializationContext context) {
        JsonObject object = (JsonObject) gson.toJsonTree(userData);
        ChatType chatType = userData.getChatType();

        if (chatType != null && chatType.equals(ChatType.GLOBAL))
            object.remove("chatType");

        if (!userData.getAuthenticated())
            object.remove("authenticated");

        if (!userData.getAltSpy())
            object.remove("altSpy");

        if (!userData.getCommandSpy())
            object.remove("commandSpy");

        if (!userData.getPingSpy())
            object.remove("pingSpy");

        if (!userData.getVanish())
            object.remove("vanish");

        if (!userData.getHideAddress())
            object.remove("hideAddress");

        return object;
    }

    @Override
    public UserData deserialize(JsonElement element, Type type, JsonDeserializationContext context) {
        UserData userData = gson.fromJson(element, UserData.class);

        if (userData.getChatType() == null)
            userData.setChatType(ChatType.GLOBAL);

        return userData;
    }

}
