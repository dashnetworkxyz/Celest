/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.command.arguments;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.chat.ChatType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Arguments {

    private final List<Object> objects = new ArrayList<>();
    private int size;
    private int index;

    public Arguments(CommandSource source, String[] text, ArgumentType... types) {
        size = types.length;
        index = 0;

        if (text.length < size)
            return;

        for (int i = 0; i < size; i++) {
            ArgumentType type = types[i];
            String arg = text[i];

            if (type.equals(ArgumentType.MESSAGE))
                arg = StringUtils.unsplit(i, ' ', text);

            Object object = type.parse(source, arg);

            if (object != null)
                objects.add(object);
        }
    }

    private int getIndex() {
        int current = index;
        index++;

        return current;
    }

    public boolean isMissing() { return objects.isEmpty() || objects.size() < size; }

    public Player getPlayer() { return (Player) objects.get(getIndex()); }

    @SuppressWarnings("unchecked")
    public List<Player> getPlayerList() { return (List<Player>) objects.get(getIndex()); }

    public RegisteredServer getServer() { return (RegisteredServer) objects.get(getIndex()); }

    public ChatType getChatType() { return (ChatType) objects.get(getIndex()); }

    public UUID getUniqueId() { return (UUID) objects.get(getIndex()); }

    public Integer getInteger() { return (Integer) objects.get(getIndex()); }

    public Long getLong() { return (Long) objects.get(getIndex()); }

    public String getString() { return (String) objects.get(getIndex()); }

}
