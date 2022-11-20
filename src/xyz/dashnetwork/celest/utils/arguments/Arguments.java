/*
 * Copyright (C) 2022 Andrew Bell - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package xyz.dashnetwork.celest.utils.arguments;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import xyz.dashnetwork.celest.utils.LazyUtils;
import xyz.dashnetwork.celest.utils.StringUtils;
import xyz.dashnetwork.celest.utils.chat.ChatType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Arguments {

    private final List<Object> objects = new ArrayList<>();
    private final int size;

    public Arguments(CommandSource source, String[] text, ArgumentType... types) {
        size = types.length;

        if (text.length < size)
            return;

        String self = null;

        if (source instanceof Player)
            self = ((Player) source).getUniqueId().toString();

        for (int i = 0; i < size; i++) {
            ArgumentType type = types[i];
            String arg = text[i];

            if (self != null && LazyUtils.anyEquals(type, ArgumentType.PLAYER, ArgumentType.PLAYER_LIST))
                arg = arg.replaceAll("@[ps]", self);

            if (type.equals(ArgumentType.MESSAGE))
                arg = StringUtils.unsplit(i, ' ', text);

            Object object = type.apply(arg);

            if (object != null)
                objects.add(object);
        }
    }

    public boolean isMissing() { return objects.isEmpty() || objects.size() < size; }

    public Player getPlayer() {
        Player player = (Player) objects.get(0);
        objects.remove(0);

        return player;
    }

    @SuppressWarnings("unchecked")
    public List<Player> getPlayerList() {
        List<Player> list = (List<Player>) objects.get(0);
        objects.remove(0);

        return list;
    }

    public RegisteredServer getServer() {
        RegisteredServer server = (RegisteredServer) objects.get(0);
        objects.remove(0);

        return server;
    }

    public ChatType getChatType() {
        ChatType type = (ChatType) objects.get(0);
        objects.remove(0);

        return type;
    }

    public UUID getUniqueId() {
        UUID uuid = (UUID) objects.get(0);
        objects.remove(0);

        return uuid;
    }

    public Integer getInteger() {
        Integer integer = (Integer) objects.get(0);
        objects.remove(0);

        return integer;
    }

    public Long getLong() {
        Long number = (Long) objects.get(0);
        objects.remove(0);

        return number;
    }

    public String getString() {
        String string = (String) objects.get(0);
        objects.remove(0);

        return string;
    }

}
