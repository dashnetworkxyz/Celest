/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.storage;

import com.velocitypowered.api.proxy.Player;
import org.yaml.snakeyaml.Yaml;
import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.chat.ChatChannel;
import xyz.dashnetwork.celest.connection.Address;
import xyz.dashnetwork.celest.connection.User;
import xyz.dashnetwork.celest.log.Logger;
import xyz.dashnetwork.celest.utils.ProfileUtil;
import xyz.dashnetwork.celest.storage.data.AddressData;
import xyz.dashnetwork.celest.storage.data.PlayerData;
import xyz.dashnetwork.celest.storage.data.UserData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class LegacyParser {

    private final Yaml yaml = new Yaml();
    private final File folder = new File(Celest.getDirectory().toFile(), "legacy");
    private final Map<UUID, UserData> userDataMap = new HashMap<>();
    private final Map<String, AddressData> addressDataMap = new HashMap<>();

    public Map<UUID, UserData> getUserData() { return userDataMap; }

    public Map<String, AddressData> getAddressData() { return addressDataMap; }

    public void read() {
        Map<String, List<String>> ips = readFile("ips");
        Map<String, Long> lastPlayed = readFile("lastplayed");
        Map<String, String> names = readFile("names");
        Map<String, String> nicknames = readFile("nicknames");
        List<String> adminchat = readFile("adminchat");
        List<String> altspy = readFile("altspy");
        List<String> commandspy = readFile("commandspy");
        List<String> ownerchat = readFile("ownerchat");
        List<String> pingspy = readFile("pingspy");
        List<String> staffchat = readFile("staffchat");
        List<String> vanish = readFile("vanish");

        if (names != null) {
            for (Map.Entry<String, String> each : names.entrySet()) {
                UUID uuid = UUID.fromString(each.getKey());

                if (uuid.getMostSignificantBits() != 0) // Make sure it's not an old bedrock account
                    userDataMap.put(uuid, new UserData(each.getValue()));
            }
        }

        if (ips != null) {
            for (Map.Entry<String, List<String>> each : ips.entrySet()) {
                String address = each.getKey();
                List<PlayerData> profiles = new ArrayList<>();

                for (String stringUuid : each.getValue()) {
                    UUID uuid = UUID.fromString(stringUuid);
                    String username;

                    if (userDataMap.containsKey(uuid)) {
                        UserData data = userDataMap.get(uuid);
                        data.setAddress(address);

                        username = data.getUsername();
                    } else if (uuid.getMostSignificantBits() != 0) {
                        username = ProfileUtil.fromUuid(uuid).getName();

                        if (username == null)
                            continue;

                        userDataMap.put(uuid, new UserData(username));
                    } else // This shouldn't happen, but in theory it can.
                        continue;

                    profiles.add(new PlayerData(uuid, username));
                }

                addressDataMap.put(address, new AddressData(profiles.toArray(PlayerData[]::new)));
            }
        }

        parseMap(nicknames, UserData::setNickName);
        parseList(staffchat, data -> data.setChannel(ChatChannel.STAFF));
        parseList(adminchat, data -> data.setChannel(ChatChannel.ADMIN));
        parseList(ownerchat, data -> data.setChannel(ChatChannel.OWNER));
        parseList(altspy, data -> data.setAltSpy(true));
        parseList(commandspy, data -> data.setCommandSpy(true));
        parseList(pingspy, data -> data.setPingSpy(true));
        parseList(vanish, data -> data.setVanish(true));
    }

    public void write() {
        for (Map.Entry<String, AddressData> each : addressDataMap.entrySet()) {
            String address = each.getKey();
            AddressData data = each.getValue();
            Address instance = Address.getAddress(address, true);

            instance.setData(data);
            instance.save();
        }

        for (Map.Entry<UUID, UserData> each : userDataMap.entrySet()) {
            UUID uuid = each.getKey();
            UserData data = each.getValue();
            Optional<Player> player = Celest.getServer().getPlayer(uuid);

            if (player.isPresent()) {
                User user = User.getUser(player.get());

                user.setData(data);
                user.save();
            } else if (!data.isObsolete()) {
                String username = data.getUsername().toLowerCase();

                Storage.write(uuid.toString(), Storage.Directory.USER, data);
                Storage.write(username, Storage.Directory.LOOKUP, uuid);
            }
        }
    }

    private <T> void parseMap(Map<String, T> map, BiConsumer<UserData, T> consumer) {
        if (map == null)
            return;

        for (Map.Entry<String, T> each : map.entrySet()) {
            UUID uuid = UUID.fromString(each.getKey());

            if (userDataMap.containsKey(uuid))
                consumer.accept(userDataMap.get(uuid), each.getValue());
        }
    }

    private void parseList(List<String> list, Consumer<UserData> consumer) {
        if (list == null)
            return;

        for (String each : list) {
            UUID uuid = UUID.fromString(each);

            if (userDataMap.containsKey(uuid))
                consumer.accept(userDataMap.get(uuid));
        }
    }

    private <T> T readFile(String name) {
        File file = new File(folder, name + ".yml");

        if (!file.exists())
            return null;

        try {
            return yaml.load(new FileReader(file));
        } catch (IOException exception) {
            Logger.throwable(exception);
            return null;
        }
    }

}
