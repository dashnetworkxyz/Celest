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

package xyz.dashnetwork.celest.connection;

import xyz.dashnetwork.celest.utils.ArrayUtil;
import xyz.dashnetwork.celest.utils.LazyUtil;
import xyz.dashnetwork.celest.utils.PunishUtil;
import xyz.dashnetwork.celest.limbo.Limbo;
import xyz.dashnetwork.celest.limbo.Savable;
import xyz.dashnetwork.celest.storage.Storage;
import xyz.dashnetwork.celest.storage.data.AddressData;
import xyz.dashnetwork.celest.storage.data.PlayerData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Address implements Savable {

    private final String address;
    private final boolean generated;
    private AddressData addressData;
    private long serverPingTime;

    private Address(String address, boolean limbo) {
        this.address = address;

        addressData = Storage.read(address, Storage.Directory.ADDRESS, AddressData.class);

        if (addressData == null) {
            addressData = new AddressData();
            generated = true;
        } else
            generated = false;

        serverPingTime = -1;

        if (limbo)
            new Limbo<>(this);
    }

    public static Address getAddress(String name, boolean shouldLimbo) {
        for (User user : User.getUsers()) {
            Address address = user.getAddress();

            if (address.address.equals(name))
                return address;
        }

        Limbo<Address> limbo = Limbo.get(Address.class, each -> each.address.equals(name));

        if (limbo != null) {
            if (shouldLimbo)
                limbo.reset();
            else
                limbo.cancel();

            return limbo.getObject();
        }

        return new Address(name, shouldLimbo);
    }

    public void removeUserIfPresent(UUID uuid) {
        List<PlayerData> list = new ArrayList<>();
        PlayerData[] profiles = addressData.getProfiles();

        for (PlayerData profile : profiles)
            if (!profile.uuid().equals(uuid))
                list.add(profile);

        addressData.setProfiles(list.toArray(PlayerData[]::new));
    }

    public void addUserIfNotPresent(UUID uuid, String username) {
        PlayerData[] profiles = addressData.getProfiles();

        for (PlayerData profile : profiles)
            if (profile.uuid().equals(uuid))
                return;

        addressData.setProfiles(ArrayUtil.add(profiles, new PlayerData(uuid, username)));
    }

    @Override
    public void save() {
        if (addressData.getProfiles().length > 0 || LazyUtil.anyTrue(PunishUtil::isValid, addressData.getMute(), addressData.getBan()))
            Storage.write(address, Storage.Directory.ADDRESS, addressData);
        else if (!generated)
            Storage.delete(address, Storage.Directory.ADDRESS); // Remove obsolete addresses.
    }

    public String getString() { return address; }

    public void setData(AddressData addressData) { this.addressData = addressData; }

    public AddressData getData() { return addressData; }

    public void setServerPingTime(long serverPingTime) { this.serverPingTime = serverPingTime; }

    public long getServerPingTime() { return serverPingTime; }

}
