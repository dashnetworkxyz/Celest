/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.storage.data.serializer;

import com.google.gson.*;
import xyz.dashnetwork.celest.utils.chat.ChatType;
import xyz.dashnetwork.celest.utils.storage.data.UserData;

import java.lang.reflect.Type;

public final class UserDataSerializer implements JsonSerializer<UserData> {

    private static final Gson gson = new GsonBuilder().create();

    @Override
    public JsonElement serialize(UserData userData, Type type, JsonSerializationContext context) {
        JsonObject object = (JsonObject) gson.toJsonTree(userData);
        ChatType chatType = userData.getChatType();

        if (chatType != null && chatType.equals(ChatType.GLOBAL))
            object.remove("chatType");

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

}
